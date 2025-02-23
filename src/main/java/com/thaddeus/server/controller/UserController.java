package com.thaddeus.server.controller;

import com.thaddeus.common.result.Result;
import com.thaddeus.common.utils.HttpClientUtil;
import com.thaddeus.pojo.dto.UserLoginDTO;
import com.thaddeus.pojo.entity.User;
import com.thaddeus.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * @Author: copper
 * @CreateTime: 2025-02-18
 * @Description: 用户控制层
 * @Version: 1.0
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping
    public Result addUser(@RequestBody User user) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        userService.addUser(user);
        return Result.success();
    }



}
