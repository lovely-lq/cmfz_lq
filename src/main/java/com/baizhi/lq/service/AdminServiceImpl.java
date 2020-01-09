package com.baizhi.lq.service;

import com.baizhi.lq.dao.AdminDao;
import com.baizhi.lq.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDao adminDao;

    @Override
    public Admin selectByAdmin(Admin admin) {
        Admin admin1 = adminDao.selectOne(admin);
        return admin1;
    }
}
