package com.baizhi.lq.test;

import com.baizhi.lq.dao.AdminDao;
import com.baizhi.lq.entity.Admin;
import com.baizhi.lq.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class testDao {
    @Autowired
    AdminDao adminDao;
    @Autowired
    AdminService adminService;


    @Test
    public void testSelectByAdmin() {
        Admin admin = new Admin(null, "admin", "admin");
        Admin admin1 = adminService.selectByAdmin(admin);
        //Admin admin1 = adminDao.selectOne(admin);
        System.out.println(admin1);
    }
}
