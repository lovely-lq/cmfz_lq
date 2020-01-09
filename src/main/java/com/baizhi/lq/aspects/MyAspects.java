package com.baizhi.lq.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
@Slf4j
public class MyAspects {
    @Autowired
    HttpServletRequest request;

    @Around(value = "pt()")
    public Object testAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object proceed;
        //Admin admin = (Admin)request.getSession().getAttribute("admin");


        //执行操作的用户
        //String name = admin.getUsername();
        //操作时间
        Date date = new Date();
        //做了什么事
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        Log annotation = method.getAnnotation(Log.class);
        String methodName = annotation.name();
        //事情的执行结果
        boolean flag = false;

        try {
            proceed = proceedingJoinPoint.proceed();   //调用目标方法
            flag = true;
            return proceed;
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            //log.info("person--->{},date--->{},thing--->{},flag--->{}", name, date, methodName, flag);
            /*LogEntry logEntry = new LogEntry(UUID.randomUUID().toString(), methodName, name, date, flag);
            logService.insert(logEntry);*/
        }
    }

    @Pointcut(value = "@annotation(com.baizhi.lq.aspects.Log)")
    public void pt() {

    }
}
