package com.thaddeus.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thaddeus.common.constant.RoomConstant;
import com.thaddeus.common.context.BaseContext;
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
    public void createRoom(Long userId) {
        User user = userService.selectByUserId(userId).getData();
        String roomName = user.getNickName()+"的房间";
        Room room = new Room(null, roomName, RoomConstant.CAPACITY, RoomConstant.ENABLE, null, null, null, null);
        roomMapper.createRoom(room);
        Long roomId = room.getRoomId();

        // TODO JOIN_TIME LEFT_TIME使用自动填充
        RoomUser roomUser = new RoomUser(null, roomId, userId, null, null);
        roomUserMapper.insert(roomUser);
    }

    public void quitRoom(Long roomId) {
        Room room = roomMapper.selectById(roomId);
        UpdateWrapper<Room> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("room_id", roomId)
                        .set("room_status", RoomConstant.DISABLE);
        int row = roomMapper.update(room, updateWrapper);
    }
}
