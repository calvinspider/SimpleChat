package org.yang.zhang.interceptor;

import javax.naming.AuthenticationException;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yang.zhang.entity.Result;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 09 26 14:45
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public Result<?> bindExceptionHandler(BindException e) {
        return Result.errorData("401",e.getMessage());
    }

}
