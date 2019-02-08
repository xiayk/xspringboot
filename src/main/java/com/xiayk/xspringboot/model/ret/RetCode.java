package com.xiayk.xspringboot.model.ret;


/**
 * 响应码枚举
 */
public enum  RetCode {

    SUCCESS(200),

    FALL(400),

    UNAUTHORIZED(401),

    NOT_FOUND(404),

    INTERNAL_SERVER_ERROR(500);

    public int code;

    RetCode(int code){
        this.code = code;
    }
}
