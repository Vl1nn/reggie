package com.ann.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
//代理使用这些注解的 类 抛出的异常
@ControllerAdvice(annotations = {RestController.class, Controller.class})
//该方法需要返回json数据
@ResponseBody
//打印日志
@Slf4j
public class GlobalExceptionHandler {
//    异常处理方法
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error("异常信息：{}",ex.getMessage());
        if (ex.getMessage().contains("Duplicate entry")) {
//            split根据空格切割异常信息
            String[] exSplit = ex.getMessage().split(" ");
            String msg = "账号"+ exSplit[2] +"已存在";
            return R.error(msg);
        }
        return R.error("服务器异常");
    }

}
