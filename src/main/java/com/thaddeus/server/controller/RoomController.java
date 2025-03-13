package com.thaddeus.server.controller;

import com.thaddeus.common.result.Result;
import com.thaddeus.pojo.entity.Room;
import com.thaddeus.server.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 通过userId创建房间
     */
    @GetMapping()
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

    @PostMapping("/join")
    public Result joinRoom(@RequestParam Long userId) {

        return Result.ok();
    }




}
