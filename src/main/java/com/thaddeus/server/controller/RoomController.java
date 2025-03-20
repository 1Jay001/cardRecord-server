package com.thaddeus.server.controller;

import com.thaddeus.common.result.Result;
import com.thaddeus.common.result.ResultCodeEnum;
import com.thaddeus.pojo.entity.Room;
import com.thaddeus.pojo.entity.User;
import com.thaddeus.pojo.vo.RoomVO;
import com.thaddeus.server.service.RoomService;
import com.thaddeus.server.service.RoomUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: copper
 * @CreateTime: 2025-03-06
 * @Description: 房间管理控制层
 * @Version: 1.0
 */
@RestController
@Slf4j
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomUserService roomUserService;

    /**
     * 通过userId创建房间
     * 同一个用户只能创建一个房间，在房间未关闭的情况下不能创建另一个房间
     */
    @PostMapping()
    public Result<Room> addRoom() {
        Room room = roomService.createRoom();
        return Result.ok(room);
    }

    /**
     * 通过roomId使room逻辑删除
     * @param roomId
     * @return
     */
    @PostMapping("/quit")
    public Result quitRoom(@RequestParam Long roomId) {
        roomService.quitRoom(roomId);
        return Result.ok();
    }


    /**
     * 获取房间信息
     * @param roomId
     * @return
     */
    @GetMapping("/list")
    public Result getRoomInfo(@RequestParam Long roomId) {
        Room room = roomService.getById(roomId);
        return Result.ok(room);
    }

    /**
     * 用户加入房间
     * 验证权限、更新房间成员列表、记录用户加入时间、明确房间的状态，不可用的房间无法加入
     * 返回完整的房间信息（包含成员列表、房间配置等）
     * @param roomId
     * @return JoinRoomVO
     */
    @PostMapping("/join")
    public Result<RoomVO> joinRoom(Long roomId) {
        if (roomId == null ) {
            return Result.build(null, ResultCodeEnum.ROOM_NON_EXISTENT);
        }
        Boolean canBeAdd = roomService.canBeAdd(roomId);
        if (canBeAdd) {
            Room roomInfo = roomService.getRoomInfo(roomId);
            RoomVO roomVO = new RoomVO();
            BeanUtils.copyProperties(roomInfo, roomVO);
            // 获取房间成员
            List<User> userListByRoomId = roomUserService.getUserListByRoomId(roomId);
            roomVO.setMembers(userListByRoomId);
            roomVO.setStatus(roomInfo.getRoomStatus());
            return Result.build(roomVO, ResultCodeEnum.SUCCESS);
        }
        return Result.build(null, ResultCodeEnum.ROOM_CANNOT_JOIN);
    }


}
