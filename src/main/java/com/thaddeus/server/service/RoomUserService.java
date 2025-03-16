package com.thaddeus.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.thaddeus.pojo.entity.RoomUser;
import com.thaddeus.pojo.entity.User;

import java.util.List;

/**
 * @Author: copper
 * @CreateTime: 2025-03-15
 * @Description: room_user表接口
 * @Version: 1.0
 */
public interface RoomUserService extends IService<RoomUser> {


    /**
     * 获取同一房间下的所有成员
     * TODO 对用户的加入时间和离开时间进行校验
     * @param roomId
     * @return
     */
    List<User> getUserListByRoomId(Long roomId);

}
