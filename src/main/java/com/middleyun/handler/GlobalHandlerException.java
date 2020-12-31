package com.middleyun.handler;

import com.middleyun.swigger.ResultVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @title   全局异常捕获
 * @description
 * @author huangwei
 * @createDate 2020/12/29
 * @version 1.0
 */
@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(Exception.class)
    public ResultVO handler(Throwable exception) {
        return ResultVO.fail(exception.getMessage());
    }

}
