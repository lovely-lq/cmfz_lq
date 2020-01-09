package com.baizhi.lq.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.excel.EasyExcel;
import com.baizhi.lq.dao.UserDAO;
import com.baizhi.lq.entity.User;
import com.baizhi.lq.entity.UserDto;
import com.baizhi.lq.entity.UserListener;
import com.baizhi.lq.service.UserService;
import io.goeasy.GoEasy;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserDAO userDao;
    @Autowired
    UserService userService;

    @RequestMapping("imageUpload")
    public void imageUpload() {
        List<User> users = userDao.selectAll();
        String url = "E:\\JAVA\\后期项目\\day6-poi\\" + new Date().getTime() + ".xls";
        EasyExcel.write(url, User.class)
                .sheet("用户")
                .doWrite(users);
    }

    @RequestMapping("leadExcel")
    public void leadExcel() {
        String url = "E:\\JAVA\\后期项目\\day6-poi\\1578050684302.xls";
        EasyExcel.read(url, User.class, new UserListener()).sheet().doRead();
    }

    @RequestMapping("queryByPage")
    public Map queryByPage(Integer page, Integer rows) {
        HashMap map = new HashMap();
        int totalCount = userDao.selectCount(null);
        int totalPage = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;
        List<User> users = userDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        map.put("records", totalCount);
        map.put("total", totalPage);
        map.put("rows", users);
        map.put("page", page);
        return map;

    }

    @RequestMapping("showUserCount")
    public Map showUserCount() {
        HashMap hashMap = new HashMap();
        ArrayList manList = new ArrayList();

        manList.add(userDao.selectUserCount("男", 1));
        manList.add(userDao.selectUserCount("男", 7));
        manList.add(userDao.selectUserCount("男", 30));
        manList.add(userDao.selectUserCount("男", 365));
        ArrayList womenList = new ArrayList();
        womenList.add(userDao.selectUserCount("女", 1));
        womenList.add(userDao.selectUserCount("女", 7));
        womenList.add(userDao.selectUserCount("女", 30));
        womenList.add(userDao.selectUserCount("女", 365));

        hashMap.put("man", manList);
        hashMap.put("women", womenList);

        return hashMap;

    }

    @RequestMapping("showUserLocation")
    public Map showUserLocation() {
        HashMap hashMap = new HashMap();

        List<UserDto> manDto = userDao.selectByLocation("男");
        System.out.println(manDto);
        List<UserDto> womenDto = userDao.selectByLocation("女");
        System.out.println(womenDto);
        hashMap.put("man", manDto);
        hashMap.put("women", womenDto);


        return hashMap;
    }


    @RequestMapping("save")
    public void save(String oper, String id, User user) {
        if ("add".equals(oper)) {
            user.setId(UUID.randomUUID().toString());
            user.setRigest_date(new Date());
            System.out.println(user);
            userDao.insert(user);
            GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-1f3d26f67d504cfcb47a5830274af472");
            Map map = showUserCount();
            String ss = JSONUtils.toJSONString(map);
            System.out.println(ss);
            goEasy.publish("cmfz", ss);
        } else if ("del".equals(oper)) {
            userDao.deleteByPrimaryKey(id);
            GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-1f3d26f67d504cfcb47a5830274af472");
            Map map = showUserCount();
            String ss = JSONUtils.toJSONString(map);
            System.out.println(ss);
            goEasy.publish("cmfz", ss);
        }

    }

    @RequestMapping("UserUpload")
    public void UserUpload(MultipartFile photo, HttpServletRequest request, String id) {
        System.out.println(id + "用户上传的id");

        userService.UserImgUpload(photo, request, id);

    }
}
