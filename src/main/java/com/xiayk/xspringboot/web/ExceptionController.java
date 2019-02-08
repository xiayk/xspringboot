package com.xiayk.xspringboot.web;

import com.xiayk.xspringboot.model.ret.RetResponse;
import com.xiayk.xspringboot.model.ret.RetResult;
import org.apache.shiro.authc.AccountException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    // 捕捉 CustomRealm 抛出的异常
    @ExceptionHandler(AccountException.class)
    public RetResult<Object> handleShiroException(Exception ex) {
        return RetResponse.makeErrRsp(ex.getMessage());
    }
}