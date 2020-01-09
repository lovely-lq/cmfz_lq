package com.baizhi.lq.service;

import com.baizhi.lq.dao.UserDAO;
import com.baizhi.lq.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;

    public void UserImgUpload(MultipartFile photo, HttpServletRequest request, String id) {
        HashMap hashMap = new HashMap();
        //获取http
        String scheme = request.getScheme();
        //获取IP
        String localhost = null;
        try {
            localhost = InetAddress.getLocalHost().toString();
        } catch (
                UnknownHostException e) {
            e.printStackTrace();
        }
        //获取端口号
        int serverPort = request.getServerPort();
        //获取项目名
        String contextPath = request.getContextPath();


        //根据相对路径获取绝对路径
        String realPath = request.getServletContext().getRealPath("/upload/article/");
        File file = new File(realPath);
        //创建文件
        if (!file.exists()) {
            file.mkdir();
        }
        //获取文件名
        String filname = photo.getOriginalFilename();
        //防止图片发生覆盖，重新给图片命名
        String newName = new Date().getTime() + "-" + filname;

        String uri = scheme + "://" + localhost.split("/")[1] + ":" + serverPort + contextPath + "/upload/article/" + newName;
        //文件上传
        try {
            photo.transferTo(new File(realPath, newName));
            User user = new User();
            user.setId(id);
            user.setPhoto(uri);
            System.out.println(uri + "--------------------service层");
            userDAO.updateByPrimaryKeySelective(user);
            hashMap.put("error", 0);
            hashMap.put("url", uri);
        } catch (
                IOException e) {
            hashMap.put("error", 1);
            e.printStackTrace();
        }

    }
}
