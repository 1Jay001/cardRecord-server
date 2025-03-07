package com.thaddeus.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.thaddeus.pojo.entity.Room;

/**
 * @Author: copper
 * @CreateTime: 2025-03-06
 * @Description: 房间管理服务层
 * @Version: 1.0
 */
public interface RoomService extends IService<Room> {


    void createRoom(Long userId);
}
