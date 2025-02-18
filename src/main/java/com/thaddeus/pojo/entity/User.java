package com.thaddeus.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: copper
 * @CreateTime: 2025-02-18
 * @Description: 用户实体类
 * @Version: 1.0
 */

@Data
@AllArgsConstructor
public class User {

    private Long userId;

    private String openId;

    private String nickName;

    private String avatarURL;

    private LocalDateTime createTime;

    private LocalDateTime lastLoginTime;
}
