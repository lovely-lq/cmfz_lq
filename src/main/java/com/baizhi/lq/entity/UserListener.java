package com.baizhi.lq.entity;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baizhi.lq.dao.UserDAO;
import com.baizhi.lq.util.ApplicationContentUtils;

import java.util.ArrayList;

public class UserListener extends AnalysisEventListener<User> {


    ApplicationContentUtils ac = new ApplicationContentUtils();
    ArrayList<User> list = new ArrayList<User>();

    @Override
    public void invoke(User user, AnalysisContext analysisContext) {
        UserDAO userDAO = (UserDAO) ac.getBean(UserDAO.class);
        userDAO.insertSelective(user);
        list.add(user);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        for (User user : list) {
            System.out.println(user + "-------------");
        }
    }
}
