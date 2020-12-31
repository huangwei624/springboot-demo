package com.middleyun.handler;

import com.alibaba.fastjson.JSON;
import com.middleyun.anno.MethodOperateLog;
import com.middleyun.anno.OperateLog;
import com.middleyun.swigger.domin.entity.RequestLog;
import eu.bitwalker.useragentutils.UserAgent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @title 日志操作切面类
 * @description 对注解了 @OperateLog 的类或者方法进行日志记录
 * @author huangwei
 * @createDate 2020/12/29
 * @version 1.0
 */
@Aspect
@Component
public class LogOperateAspect {

    @Pointcut("execution( * com.middleyun.swigger..*Controller.*(..))")
    public void pointCut() {

    }

    @AfterReturning(pointcut="pointCut()", returning="result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        handlerRequestLog(joinPoint, result, null);
    }

    @AfterThrowing(pointcut = "pointCut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        handlerRequestLog(joinPoint, null, e);
    }

    /**
     * 处理请求
     * @param joinPoint
     * @param e
     */
    private void handlerRequestLog(JoinPoint joinPoint, Object result, Throwable e) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        OperateLog operateLog = AnnotationUtils.findAnnotation(method.getDeclaringClass(), OperateLog.class);
        if (operateLog != null) {
            // 获取模块名称
            String moduleName = operateLog.moduleName();

            // 获取方法描述
            MethodOperateLog methodOperateLog = method.getAnnotation(MethodOperateLog.class);
            String description = methodOperateLog.description();

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            // 请求url
            String requestURI = request.getRequestURI();
            // 请求主机
            String remoteHost = request.getRemoteHost();
            // 请求方法
            String requestMethod = request.getMethod();
            // 请求参数
            String requestParam = JSON.toJSONString(joinPoint.getArgs());

            UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
            // 浏览器名字
            String browserName = userAgent.getBrowser().getName();
            // 操作系统
            String operatingSystem = userAgent.getOperatingSystem().getName();

            String responseResult;
            String responseStatus;
            // 方法执行有异常
            if (e != null) {
                responseResult = e.getMessage();
                responseStatus = "失败";
            } else {
                // 方法正常结束
                responseResult = JSON.toJSONString(result);
                responseStatus = "成功";
            }

            RequestLog requestLog = RequestLog.builder().host(remoteHost).methodDescription(description).moduleName(moduleName).requestParams(requestParam)
                    .requestUrl(requestURI).responseResult(responseResult).browersName(browserName).operatingSystem(operatingSystem)
                    .requestMethod(requestMethod).responseStatus(responseStatus).build();

            System.out.println(requestLog);
        }
    }


}
