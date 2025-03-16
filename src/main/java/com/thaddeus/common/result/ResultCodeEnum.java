package com.thaddeus.common.result;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {
    SUCCESS(200, "成功"),
    FAIL(201, "失败"),
    LOGIN_ERR(203, "登录失败"),
    UPLOAD_FAILED(204, "文件上传失败"),
    NO_PERMISSION(205, "没有权限"),
    NOEXIST_USER(206, "用户不存在"),
    FAIL_CREATEROOM(207, "创建房间失败"),
    ROOM_NON_EXISTENT(208, "房间不存在")

    ;

    private Integer code;
    private String  message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
