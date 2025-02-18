package com.thaddeus.server.controller;

import com.thaddeus.common.result.Result;
import com.thaddeus.pojo.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: copper
 * @CreateTime: 2025-02-18
 * @Description: 用户控制层
 * @Version: 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping()
    public Result addUser(@RequestBody User user) {

        return Result.success();
    }

}
