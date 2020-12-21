package com.shanjupay.merchant.common.intercept;

import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.common.domain.CommonErrorCode;
import com.shanjupay.common.domain.ErrorCode;
import com.shanjupay.common.domain.RestErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mercurius
 * @version 1.0
 * @date 2020/11/23 15:09
 */
@ControllerAdvice//与@ExceptionHandler配合使用实现全局异常处理
public class GlobalExceptionHandler {
    private static final Logger logger=LoggerFactory.getLogger(GlobalExceptionHandler.class);
    //捕获异常
    @ExceptionHandler(value=Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse processException(HttpServletResponse response,
                                              HttpServletRequest request,Exception e){
        //解析异常信息
        //如果系统自定义异常，直接取出errCode和errMessage
        if(e instanceof BusinessException){
            logger.info(e.getMessage(),e);
            //解析系统自定义异常信息
            BusinessException businessException= (BusinessException) e;
            ErrorCode errorCode=businessException.getErrorCode();
            //错误代码
            int code=errorCode.getCode();
            //错误信息
            String desc=errorCode.getDesc();
            return new RestErrorResponse(String.valueOf(code),desc);
        }

        logger.error("系统异常：",e);
        //统一定义为9999系统未知异常
        return new RestErrorResponse(String.valueOf(CommonErrorCode.UNKNOWN.getCode()),CommonErrorCode.UNKNOWN.getDesc());
    }
}

