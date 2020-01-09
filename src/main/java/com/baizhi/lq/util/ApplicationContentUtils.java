package com.baizhi.lq.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContentUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContentUtils.applicationContext = applicationContext;
    }

    public Object getBean(Class clazz) {
        return applicationContext.getBean(clazz);
    }
}