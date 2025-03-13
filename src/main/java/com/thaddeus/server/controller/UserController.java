package com.thaddeus.server.controller;

import com.thaddeus.common.result.Result;
import com.thaddeus.common.result.ResultCodeEnum;
import com.thaddeus.pojo.dto.UserDTO;
import com.thaddeus.pojo.entity.User;
import com.thaddeus.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        userService.addUser(user);
        return Result.ok();
    }

    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    @GetMapping("/list")
    public Result getUser(@RequestParam Long userId) {
        User user = userService.selectByUserId(userId).getData();
        return Result.build(user, ResultCodeEnum.SUCCESS);
    }

    /**
     * 修改用户信息
     * @param userDTO {useId, avatarUrl, nickName}
     * @return
     */
    @PostMapping("/updateInfo")
    public Result updateUserInfo(@RequestBody UserDTO userDTO) {
        if (userService.updateUser(userDTO).getCode() != 200) {
            return Result.fail();
        }
        return Result.ok();
    }

}
