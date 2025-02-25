package com.thaddeus.common.result;

import lombok.Getter;

/**
 * @program:guigu-oa-parent
 * @author: 1Jay001
 * @Time: 2023/5/15  17:17
 * @description:
 */
@Getter
public enum ResultCodeEnum {
    SUCCESS(200, "成功"),
    FAIL(201, "失败"),
//    LOGIN_ERR(204, "登录失败");
    LOGIN_ERR(208, "登录失败");

    private Integer code;
    private String  message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
