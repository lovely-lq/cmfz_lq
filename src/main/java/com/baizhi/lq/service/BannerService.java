package com.baizhi.lq.service;

import com.baizhi.lq.entity.Banner;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface BannerService {
    //分页展示
    public HashMap<String, Object> showAllPage(Integer page, Integer rows);

    //添加
    public void addBanner(Banner b);

    //删除
    public void deleteBanner(String id);

    //修改
    public void updateBanner(Banner b);

    //文件上传
    public void bannerUpload(MultipartFile url, String id, HttpServletRequest request);
}
