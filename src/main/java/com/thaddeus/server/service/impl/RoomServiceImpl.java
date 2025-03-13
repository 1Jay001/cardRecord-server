package com.thaddeus.server.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thaddeus.common.constant.RoomConstant;
import com.thaddeus.common.exception.BaseException;
import com.thaddeus.common.result.Result;
import com.thaddeus.common.result.ResultCodeEnum;
import com.thaddeus.pojo.entity.Room;
import com.thaddeus.pojo.entity.RoomUser;
import com.thaddeus.pojo.entity.User;
import com.thaddeus.pojo.vo.RoomVO;
import com.thaddeus.server.mapper.RoomMapper;
import com.thaddeus.server.mapper.RoomUserMapper;
import com.thaddeus.server.service.RoomService;
import com.thaddeus.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
        /**
         * 1. 后端从token获取userId，前端不必传入userId
         * 2. 同一个userId可以创建多个room，用户退出房间时room_status置为disable
         */
        Long currentId = StpUtil.getLoginIdAsLong();
        User user = userService.selectByUserId(currentId).getData();
        String roomName = user.getNickName()+"的房间";
        Room room = new Room(null, roomName, RoomConstant.CAPACITY, RoomConstant.ENABLE, null, null, null, null);
        roomMapper.createRoom(room); // 主键回显
        Long roomId = room.getRoomId();

        // TODO JOIN_TIME LEFT_TIME使用自动填充
        RoomUser roomUser = new RoomUser(null, roomId, currentId, LocalDateTime.now(), null);
        int insert = roomUserMapper.insert(roomUser);
        if (insert == 0) {
            throw new BaseException(207, "创建房间失败"); // TODO 异常处理有待改进
        }
        Room savedRoom = roomMapper.selectById(roomId);
        return room;
    }

    public Result quitRoom(Long roomId) {
        Room room = roomMapper.selectById(roomId);
        UpdateWrapper<Room> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("room_id", roomId)
                        .set("room_status", RoomConstant.DISABLE);
        int row = roomMapper.update(room, updateWrapper);
        return null;
    }

    public Result<RoomVO> getRoomInfo() {
        return null;
    }

    public Result joinRoom(Long userId) {

        return null;
    }



}
