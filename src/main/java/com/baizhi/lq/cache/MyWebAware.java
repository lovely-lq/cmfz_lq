package com.baizhi.lq.cache;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class MyWebAware implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static Object getByName(String name) {
        Object bean = applicationContext.getBean(name);
        return bean;
    }

    public static Object getByClazz(Class clazz) {
        Object bean = applicationContext.getBean(clazz);
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
