package com.thaddeus.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.thaddeus.common.enumeration.OperationType;
import com.thaddeus.pojo.entity.Room;
import com.thaddeus.server.annotation.AutoFill;

/**
 * @Author: copper
 * @CreateTime: 2025-03-06
 * @Description: 房间管理服务层
 * @Version: 1.0
 */
public interface RoomService extends IService<Room> {
    void createRoom(Long userId);

    void quitRoom(Long roomId);
}
