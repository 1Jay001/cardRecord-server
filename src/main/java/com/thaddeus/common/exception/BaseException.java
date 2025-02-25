package com.thaddeus.common.exception;

import com.thaddeus.common.result.ResultCodeEnum;
import lombok.Data;

/**
 * @Author: copper
 * @CreateTime: 2025-02-23
 * @Description: 异常类
 * @Version: 1.0
 */
@Data
public class BaseException extends RuntimeException{

    private Integer code; // 状态码
    private String msg; // 描述信息

    public BaseException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    /**
     * 接收枚举类型对象
     *
     * @param resultCodeEnum
     */
    public BaseException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMessage();
    }

    @Override
    public String toString() {
        return "BaseException{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
