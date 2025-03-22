package com.thaddeus.server.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thaddeus.common.constant.RoomConstant;
import com.thaddeus.common.exception.BaseException;
import com.thaddeus.pojo.entity.Room;
import com.thaddeus.pojo.entity.RoomUser;
import com.thaddeus.pojo.entity.User;
import com.thaddeus.server.mapper.RoomMapper;
import com.thaddeus.server.mapper.RoomUserMapper;
import com.thaddeus.server.service.RoomService;
import com.thaddeus.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @Author: copper
 * @CreateTime: 2025-03-06
 * @Description: 房间管理服务层实现类
 * @Version: 1.0
 */
@Service
@Slf4j
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

        // 获取该用户创建的所有房间
        LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Room::getCreateUser, currentId)
               .select(Room::getRoomId, Room::getRoomStatus, Room::getCreateTime);
        List<Room> roomIdsListBySameCreator = roomMapper.selectList(wrapper);

        if (roomIdsListBySameCreator.size() == 0) {
            // 不存在未关闭的房间 -> 创建房间
            roomMapper.createRoom(room); // 主键回显
            Long roomId = room.getRoomId();
            RoomUser roomUser = new RoomUser(null, roomId, currentId, LocalDateTime.now(), null);
            int insert = roomUserMapper.insert(roomUser);
            if (insert == 0) {
                throw new BaseException(207, "创建房间失败"); // TODO 异常处理有待改进
            }
            Room savedRoom = roomMapper.selectById(roomId);
            return savedRoom;
        } else {
            // 返回最新的未关闭的房间 TODO: 对之前未关闭的房间进行处理
            Optional<Long> optional = roomIdsListBySameCreator.stream()
                    .filter(r -> r.getRoomStatus().equals(RoomConstant.ENABLE))
                    .filter(r -> r.getCreateTime() != null)
                    .max(Comparator.comparing(Room::getCreateTime))
                    .map(Room::getRoomId);
            Long latestDisableRoomId = optional.get();
            Room latestDisableRoom = roomMapper.selectById(latestDisableRoomId);
            return latestDisableRoom;
        }

    }

    public void quitRoom(Long roomId) {
        Room room = roomMapper.selectById(roomId);
        if (room != null) {
            LambdaUpdateWrapper<Room> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(Room::getRoomId, roomId)
                   .eq(Room::getRoomStatus, RoomConstant.ENABLE) // 房间可操作
                   .set(Room::getRoomStatus, RoomConstant.DISABLE);
            int row = roomMapper.update(room, wrapper);
            log.info(String.valueOf(row));
            if (row == 0) {
                throw new BaseException(210, "退出房间失败（房间不可操作或状态已变更）");
            } else {
                LambdaUpdateWrapper<RoomUser> roomUserWrapper = new LambdaUpdateWrapper<>();
                roomUserWrapper.eq(RoomUser::getUserId, StpUtil.getLoginIdAsLong())
                               .eq(RoomUser::getRoomId, roomId)
                               .set(RoomUser::getLeftTime, LocalDateTime.now());
                int update = roomUserMapper.update(roomUserWrapper);
                if (update == 0) {
                    throw new BaseException(211, "添加用户离开房间的时间失败");
                }
            }
        } else {
            throw new BaseException(208, "房间不存在");
        }
    }

    public Room getRoomInfo(Long roomId) {
        Room room = roomMapper.selectById(roomId);
        if (room.getRoomStatus().equals(RoomConstant.DISABLE)) {
            throw new BaseException(201, "房间不可用");
        }
        return room;
    }

    public Boolean canBeAdd(Long roomId) {
        // TODO 后期校验 roomSize, 超过房间人数不可加入

        Room room = roomMapper.selectById(roomId);
        if (room != null) {
            Integer roomStatus = room.getRoomStatus();
            if (roomStatus == RoomConstant.ENABLE) {
                return true;
            }
            return false;
        }
        throw new BaseException(208, "房间不存在");
    }
}
