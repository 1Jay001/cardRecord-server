package com.thaddeus.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Author: copper
 * @CreateTime: 2025-02-23
 * @Description: 封装token和用户信息
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private String token;
    private Map<String, String> userInfo;
}
