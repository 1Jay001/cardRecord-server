package com.thaddeus.server.service;

import com.thaddeus.pojo.entity.WeChatEntity;

/**
 * @Author: copper
 * @CreateTime: 2025-02-22
 * @Description: 用户登录服务层
 * @Version: 1.0
 */
public interface UserLoginService {

    /**
     * 获取openid和session_key添加到user_wechat表中
     * @param weChatEntity
     */
    void addWechat(WeChatEntity weChatEntity);

}
