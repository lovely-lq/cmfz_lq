package com.baizhi.lq.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CacheAspects {
    @Autowired
    RedisTemplate redisTemplate;

    @Around(value = "@annotation(com.baizhi.lq.aspects.AddOrSelectCache)")
    public Object addOrSelectCache(ProceedingJoinPoint proceedingJoinPoint) {
        String clazz = proceedingJoinPoint.getTarget().getClass().toString();
        String name = proceedingJoinPoint.getSignature().getName();
        Object[] args = proceedingJoinPoint.getArgs();
        String key = name;
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            key += arg;
        }

        //查询数据库之前先查询缓存数据
        Object o = redisTemplate.opsForHash().get(clazz, key);

        //如果查询到返回缓存中的数据
        if (o != null) {
            return o;

        }
        //查询不到 数据库查询  添加数据到缓存
        try {
            Object proceed = proceedingJoinPoint.proceed();//返回切入方法的返回值
            redisTemplate.opsForHash().put(clazz, key, proceed);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    @Around(value = "@annotation(com.baizhi.lq.aspects.ClearCache)")
    public Object ClearCache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String s = proceedingJoinPoint.getTarget().getClass().toString();
        redisTemplate.delete(s);
        return proceedingJoinPoint.proceed();
    }
}
