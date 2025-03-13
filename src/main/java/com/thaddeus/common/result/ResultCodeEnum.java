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
    LOGIN_ERR(203, "登录失败"),
    UPLOAD_FAILED(204, "文件上传失败"),
    NO_PERMISSION(205, "没有权限"),
    NOEXIST_USER(206, "用户不存在"),
    FAIL_CREATEROOM(207, "创建房间失败"),

    ;

    private Integer code;
    private String  message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
