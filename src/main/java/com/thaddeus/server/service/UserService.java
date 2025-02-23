package com.thaddeus.server.service;

import com.thaddeus.pojo.entity.User;
import com.thaddeus.pojo.entity.WeChatEntity;

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
    User selectByOpenId(String openId);
}
