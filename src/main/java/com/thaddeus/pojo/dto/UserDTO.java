package com.thaddeus.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: copper
 * @CreateTime: 2025-03-08
 * @Description: 前端修改用户信息传入对象
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String avatarUrl;

    private String nickName;
}
