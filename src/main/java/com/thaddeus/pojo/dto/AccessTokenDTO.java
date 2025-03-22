package com.thaddeus.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: copper
 * @CreateTime: 2025-03-22
 * @Description: 获取接口调用凭据
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenDTO {

    private String accessToken; // 获取到的凭证
    private String expiresIn; // 凭证有效时间，单位：秒
}
