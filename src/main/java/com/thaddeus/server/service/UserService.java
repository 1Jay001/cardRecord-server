package com.thaddeus.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.thaddeus.common.enumeration.OperationType;
import com.thaddeus.common.result.Result;
import com.thaddeus.pojo.entity.User;
import com.thaddeus.pojo.entity.WeChatEntity;
import com.thaddeus.server.annotation.AutoFill;

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

}
