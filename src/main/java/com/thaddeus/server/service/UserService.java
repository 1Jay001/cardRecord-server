package com.thaddeus.server.service;

import com.thaddeus.common.result.Result;
import com.thaddeus.pojo.dto.UserDTO;
import com.thaddeus.pojo.entity.User;
/**
 * @Author: copper
 * @CreateTime: 2025-02-18
 * @Description: 用户接口
 * @Version: 1.0
 */
public interface UserService {

    /**
     * 添加用户到用户表
     * @param user
     */
    void addUser(User user);

    /**
     * 根据openid查询用户
     * @param openId 一个openid对应一个用户
     * @return
     */
    Result<User> selectByOpenId(String openId);

    /**
     * 根据userId查询用户
     * @param userId
     * @return
     */
    Result<User> selectByUserId(Long userId);

    /**
     * 更新用户信息
     * @param userDTO
     */
    Result updateUser(UserDTO userDTO);
}
