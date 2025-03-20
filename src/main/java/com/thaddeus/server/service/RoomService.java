package com.thaddeus.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.thaddeus.common.enumeration.OperationType;
import com.thaddeus.common.result.Result;
import com.thaddeus.pojo.dto.JoinRoomDTO;
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
     * 创建房间: 一个用户能创建多个房间，一个房间只能有一个被创建者
     * 1. 后端从token获取userId，前端不必传入userId
     * 2. 在房间未被正确退出时，无法继续创建房间
     */
    @AutoFill(OperationType.INSERT)
    Room createRoom();

    /**
     * 关闭房间
     * @param roomId
     */
    Result quitRoom(Long roomId);

    /**
     * 用户加入房间后获取房间信息
     * @return JoinRoomDTO
     */
    Room getRoomInfo(Long roomId);


    /**
     * 根据roomId检查room是否可以被加入
     * @param roomId
     * @return
     */
    Boolean canBeAdd(Long roomId);
}
