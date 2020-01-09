package com.baizhi.lq.service;

import com.baizhi.lq.aspects.AddOrSelectCache;
import com.baizhi.lq.aspects.Log;
import com.baizhi.lq.dao.BannerDao;
import com.baizhi.lq.entity.Banner;
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
public class BannerServiceImpl implements BannerService {
    @Autowired
    BannerDao bannerDao;

    @Override
    @AddOrSelectCache
    public HashMap<String, Object> showAllPage(Integer page, Integer rows) {
        //创建一个map集合
        HashMap<String, Object> map = new HashMap<>();
        //当前页
        Integer start = (page - 1) * rows;
        List<Banner> banners = bannerDao.findByPage(start, rows);
        map.put("rows", banners);
        //页号
        map.put("page", page);
        //总条数
        Integer totalCount = bannerDao.totalCount();
        map.put("records", totalCount);
        //令总页数为0   如果总条数/每页显示得条数为0，有总页数
        //总页数
        Integer pageCount = 0;
        if (totalCount % rows == 0) {
            pageCount = totalCount / rows;
        } else {
            pageCount = totalCount / rows + 1;
        }
        map.put("total", pageCount);
        return map;

    }

    @Log(name = "轮播图的添加")
    @Override
    public void addBanner(Banner b) {
        b.setId(UUID.randomUUID().toString());
        b.setStatus("2");
        b.setHref("jhjkdnl");
        bannerDao.add(b);
    }

    @Log(name = "轮播图的删除")
    @Override
    public void deleteBanner(String id) {
        bannerDao.delete(id);
    }

    @Log(name = "轮播图的修改")
    @Override
    public void updateBanner(Banner b) {
        bannerDao.update(b);

    }

    @Override
    public void bannerUpload(MultipartFile url, String id, HttpServletRequest request) {
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
        String filname = url.getOriginalFilename();
        //防止图片发生覆盖，重新给图片命名
        String newName = new Date().getTime() + "-" + filname;

        String uri = scheme + "://" + localhost.split("/")[1] + ":" + serverPort + contextPath + "/upload/photo/" + newName;
        //文件上传
        try {
            url.transferTo(new File(realPath, newName));
            //修改轮播图得信息
            Banner banner = new Banner();
            banner.setId(id);
            banner.setUrl(uri);
            System.out.println(banner);
            //调用修改方法
            bannerDao.update(banner);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
