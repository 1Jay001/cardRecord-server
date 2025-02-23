package com.thaddeus.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: copper
 * @CreateTime: 2025-02-22
 * @Description: user_wechat实体类
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeChatEntity {

    private String userId;
    private String openId;
    private String sessionKey;
}
