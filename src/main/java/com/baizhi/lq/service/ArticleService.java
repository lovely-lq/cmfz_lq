package com.baizhi.lq.service;

import com.baizhi.lq.entity.Article;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public interface ArticleService {
    //分页展示
    public HashMap<String, Object> queryAllPage(Integer page, Integer rows);

    //添加
    public void addArticle(Article article);

    //修改
    public void updateArticle(Article article);

    //删除
    public void deleteArticle(String id);

    //文件上传
    public Map articleUpload(MultipartFile img, String id, HttpServletRequest request);

    //文件上传
    public String articleUploa(MultipartFile inputfile, String id, HttpServletRequest request);
}
