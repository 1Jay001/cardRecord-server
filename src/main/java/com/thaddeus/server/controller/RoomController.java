package com.thaddeus.server.controller;

import com.thaddeus.common.result.Result;
import com.thaddeus.server.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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




}
