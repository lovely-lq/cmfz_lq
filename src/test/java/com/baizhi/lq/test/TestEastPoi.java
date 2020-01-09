package com.baizhi.lq.test;

import com.alibaba.excel.EasyExcel;
import com.baizhi.lq.dao.UserDAO;
import com.baizhi.lq.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestEastPoi {
    @Autowired
    UserDAO userDao;

    @Test
    public void testEasy() {
        List<User> users = userDao.selectAll();
        String url = "E:\\JAVA\\后期项目\\day6-poi\\" + new Date().getTime() + ".xls";
        EasyExcel.write(url, User.class)
                .sheet("用户")
                .doWrite(users);
    }
}
