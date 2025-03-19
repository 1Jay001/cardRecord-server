package com.thaddeus.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thaddeus.pojo.entity.RoomUser;
import com.thaddeus.pojo.entity.User;
import com.thaddeus.server.mapper.RoomUserMapper;
import com.thaddeus.server.mapper.UserMapper;
import com.thaddeus.server.service.RoomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: copper
 * @CreateTime: 2025-03-15
 * @Description:
 * @Version: 1.0
 */
@Service
public class RoomUserServiceImpl extends ServiceImpl<RoomUserMapper, RoomUser> implements RoomUserService {

    @Autowired
    private RoomUserMapper roomUserMapper;

    @Autowired
    private UserMapper userMapper;

    public List<User> getUserListByRoomId(Long roomId) {
        if (roomId != null) {
            LambdaQueryWrapper<RoomUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(RoomUser::getRoomId, roomId)
                    .select(RoomUser::getUserId);
            List<RoomUser> roomUsers = roomUserMapper.selectList(wrapper);
            Set<Long> ids = roomUsers.stream().map(RoomUser::getUserId).collect(Collectors.toSet());
            List<User> userList = userMapper.selectByIds(ids);
            return userList;
        }
        log.error("roomId为空");
       return null; // TODO 避免空指针异常
    }

}
