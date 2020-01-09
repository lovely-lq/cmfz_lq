package com.baizhi.lq.service;


import com.baizhi.lq.aspects.Log;
import com.baizhi.lq.dao.AlbumDao;
import com.baizhi.lq.entity.Album;
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
import java.util.List;
import java.util.UUID;

@Service
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumDao albumDao;

    @Override
    public HashMap<String, Object> queryAllPage(Integer page, Integer rows) {
        //创建一个map集合
        HashMap<String, Object> map = new HashMap<>();
        //当前页
        Integer start = (page - 1) * rows;
        List<Album> albums = albumDao.queryByPage(start, rows);
        map.put("rows", albums);
        //页号
        map.put("page", page);
        //总条数
        Integer totalCount = albumDao.queryCount();
        map.put("records", totalCount);
        Integer pageCount = 0;
        if (totalCount % rows == 0) {
            pageCount = totalCount / rows;
        } else {
            pageCount = totalCount / rows + 1;
        }
        map.put("total", pageCount);
        return map;
    }


    @Override
    @Log(name = "专辑的发布")
    public void addAblum(Album album) {
        album.setId(UUID.randomUUID().toString());
        albumDao.addAblum(album);
    }

    @Log(name = "专辑的修改")
    @Override
    public void updateAlbum(Album album) {
        albumDao.updateAlbum(album);
    }

    @Log(name = "专辑的删除")
    @Override
    public void deleteAlbum(String id) {
        albumDao.deleteAlbum(id);
    }

    @Log(name = "专辑的上传")
    @Override
    public void albumUpload(MultipartFile headSrc, String id, HttpServletRequest request) {
        //获取http
        String scheme = request.getScheme();
        //获取IP
        String localhost = null;
        try {
            localhost = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //获取端口号
        int serverPort = request.getServerPort();
        //获取项目名
        String contextPath = request.getContextPath();


        //根据相对路径获取绝对路径
        String realPath = request.getServletContext().getRealPath("/upload/photo/");
        File file = new File(realPath);
        //创建文件
        if (!file.exists()) {
            file.mkdir();
        }
        //获取文件名
        String filname = headSrc.getOriginalFilename();
        //防止图片发生覆盖，重新给图片命名
        String newName = new Date().getTime() + "-" + filname;

        String uri = scheme + "://" + localhost.split("/")[1] + ":" + serverPort + contextPath + "/upload/photo/" + newName;
        //文件上传
        try {
            headSrc.transferTo(new File(realPath, newName));
            //修改轮播图得信息
            Album a = new Album();
            a.setId(id);
            a.setHeadSrc(uri);
            System.out.println(a);
            //调用修改方法
            albumDao.updateAlbum(a);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
