package com.baizhi.lq.service;

import com.baizhi.lq.entity.Album;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface AlbumService {
    //分页展示
    public HashMap<String, Object> queryAllPage(Integer page, Integer rows);

    //添加
    public void addAblum(Album album);

    //修改
    public void updateAlbum(Album album);

    //删除
    public void deleteAlbum(String id);

    //文件上传
    public void albumUpload(MultipartFile headSrc, String id, HttpServletRequest request);
}
