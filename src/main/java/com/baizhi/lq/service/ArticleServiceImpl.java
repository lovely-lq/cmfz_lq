package com.baizhi.lq.service;

import com.baizhi.lq.dao.ArticleDao;
import com.baizhi.lq.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleDao articleDao;

    @Override
    public HashMap<String, Object> queryAllPage(Integer page, Integer rows) {
        //创建一个map集合
        HashMap<String, Object> map = new HashMap<>();
        //当前页
        Integer start = (page - 1) * rows;
        List<Article> articles = articleDao.queryByPage(start, rows);
        map.put("rows", articles);
        //页号
        map.put("page", page);
        //总条数
        Integer totalCount = articleDao.queryCount();
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
    public void addArticle(Article article) {
        article.setId(UUID.randomUUID().toString());
        article.setCreateDate(new Date());
        article.setPublishDate(new Date());
        articleDao.addArticle(article);
    }

    @Override
    public void updateArticle(Article article) {

        articleDao.updateArticle(article);
    }

    @Override
    public void deleteArticle(String id) {
        articleDao.deleteArticle(id);
    }

    @Override
    public Map articleUpload(MultipartFile imgFile, String id, HttpServletRequest request) {
        HashMap hashMap = new HashMap();
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
        String realPath = request.getServletContext().getRealPath("/upload/article/");
        File file = new File(realPath);
        //创建文件
        if (!file.exists()) {
            file.mkdir();
        }
        //获取文件名
        String filname = imgFile.getOriginalFilename();
        //防止图片发生覆盖，重新给图片命名
        String newName = new Date().getTime() + "-" + filname;

        String uri = scheme + "://" + localhost.split("/")[1] + ":" + serverPort + contextPath + "/upload/article/" + newName;
        //文件上传
        try {
            imgFile.transferTo(new File(realPath, newName));
            //修改轮播图得信息
            Article a = new Article();
            a.setId(id);
            a.setImg(uri);
            System.out.println(a);
            //调用修改方法
            articleDao.updateArticle(a);
            hashMap.put("error", 0);
            hashMap.put("url", uri);
        } catch (IOException e) {
            hashMap.put("error", 1);
            e.printStackTrace();
        }
        return hashMap;
    }

    @Override
    public String articleUploa(MultipartFile inputfile, String id, HttpServletRequest request) {
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
        String realPath = request.getServletContext().getRealPath("/upload/article/");
        File file = new File(realPath);
        //创建文件
        if (!file.exists()) {
            file.mkdir();
        }
        //获取文件名
        String filname = inputfile.getOriginalFilename();
        //防止图片发生覆盖，重新给图片命名
        String newName = new Date().getTime() + "-" + filname;

        String uri = scheme + "://" + localhost.split("/")[1] + ":" + serverPort + contextPath + "/upload/article/" + newName;
        //文件上传
        try {
            inputfile.transferTo(new File(realPath, newName));
            //修改轮播图得信息
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }
}
