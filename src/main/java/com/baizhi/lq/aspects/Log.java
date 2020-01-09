package com.baizhi.lq.aspects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
//当前注解作用在类上 方法上 还是属性上
@Target({ElementType.METHOD})
public @interface Log {
    String name();
}
