package com.thaddeus.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author: copper
 * @CreateTime: 2025-02-18
 * @Description: 用户实体类
 * @Version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
//@TableName("`user`")
public class User {

    @TableId(type = IdType.AUTO)
    private Long userId;

    private String openId;

    private String nickName;

    private String avatarUrl;

    private LocalDateTime createTime;

    private LocalDateTime lastLoginTime;
}
