package com.thaddeus.server.controller;

import com.thaddeus.common.result.Result;
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
    @PostMapping()
    public Result addRoom(@RequestParam Long userId) {
        roomService.createRoom(userId);
        return Result.ok();
    }

    @PostMapping("/quit")
    public Result quitRoom(@RequestParam Long roomId) {
        roomService.quitRoom(roomId);
        return Result.ok();
    }




}
