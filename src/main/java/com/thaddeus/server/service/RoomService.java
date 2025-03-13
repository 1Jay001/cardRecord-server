package com.thaddeus.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.thaddeus.common.enumeration.OperationType;
import com.thaddeus.common.result.Result;
import com.thaddeus.pojo.entity.Room;
import com.thaddeus.pojo.vo.RoomVO;
import com.thaddeus.server.annotation.AutoFill;

/**
 * @Author: copper
 * @CreateTime: 2025-03-06
 * @Description: 房间管理服务层
 * @Version: 1.0
 */
public interface RoomService extends IService<Room> {

    /**
     * 创建房间
     */
    @AutoFill(OperationType.INSERT)
    Room createRoom();

    /**
     * 关闭房间
     * @param roomId
     */
    Result quitRoom(Long roomId);

    /**
     * 获取房间信息
     * @return
     */
    Result<RoomVO> getRoomInfo();

    /**
     * 用户加入房间
     * @param userId
     * @return
     */
    Result joinRoom(Long userId);
}
