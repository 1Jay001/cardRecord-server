package com.thaddeus.server.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thaddeus.common.constant.RoomConstant;
import com.thaddeus.common.exception.BaseException;
import com.thaddeus.common.result.Result;
import com.thaddeus.pojo.entity.Room;
import com.thaddeus.pojo.entity.RoomUser;
import com.thaddeus.pojo.entity.User;
import com.thaddeus.server.mapper.RoomMapper;
import com.thaddeus.server.mapper.RoomUserMapper;
import com.thaddeus.server.service.RoomService;
import com.thaddeus.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: copper
 * @CreateTime: 2025-03-06
 * @Description: 房间管理服务层实现类
 * @Version: 1.0
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private RoomUserMapper roomUserMapper;

    @Autowired
    private UserService userService;

    @Transactional
    public Room createRoom() {
        StpUtil.checkLogin();
        Long currentId = StpUtil.getLoginIdAsLong();
        if (currentId == null) {
            throw new BaseException(206, "用户不存在");
        }
        User user = userService.selectByUserId(currentId).getData();
        String roomName = user.getNickName() + "的房间";
        Room room = new Room(null, roomName, RoomConstant.CAPACITY, RoomConstant.ENABLE, null, null, null, null);
        roomMapper.createRoom(room); // 主键回显
        Long roomId = room.getRoomId();
        // 获取该用户创建的所有房间
        LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Room::getCreateUser, currentId)
                .select(Room::getRoomId, Room::getRoomStatus, Room::getCreateTime);
        List<Room> roomIdsListBySameCreator = roomMapper.selectList(wrapper);
        if (roomIdsListBySameCreator.size() == 0) {
            // 不存在未关闭的房间 -> 创建房间
            RoomUser roomUser = new RoomUser(null, roomId, currentId, LocalDateTime.now(), null);
            int insert = roomUserMapper.insert(roomUser);
            if (insert == 0) {
                throw new BaseException(207, "创建房间失败"); // TODO 异常处理有待改进
            }
            Room savedRoom = roomMapper.selectById(roomId);
            return savedRoom;
        } else {
            // 返回最新的未关闭的房间
            Optional<Long> optional = roomIdsListBySameCreator.stream()
                    .filter(enableRoom -> room.getRoomStatus().equals(RoomConstant.ENABLE))
                    .filter(createTime -> room.getCreateTime() != null)
                    .max(Comparator.comparing(Room::getCreateTime))
                    .map(Room::getRoomId);
            Long latestDisableRoomId = optional.get();
            Room latestDisableRoom = roomMapper.selectById(latestDisableRoomId);
            return latestDisableRoom;
        }

    }

    public Result quitRoom(Long roomId) {
        Room room = roomMapper.selectById(roomId);
        UpdateWrapper<Room> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("room_id", roomId)
                        .set("room_status", RoomConstant.DISABLE);
        int row = roomMapper.update(room, updateWrapper);
        return null;
    }

    public Room getRoomInfo(Long roomId) {
        /**
         * 1、判断roomId是否为空，如果为空则无法加入该房间
         * 2. 判断room状态是否可用
         */
        if (roomId == null) {
            return null;
        }
        Room room = roomMapper.selectById(roomId);
        if (room.getRoomStatus().equals(0)) {
            throw new BaseException(201, "房间不可用");
        }
        return room;
    }
}
