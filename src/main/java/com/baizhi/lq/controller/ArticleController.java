package com.baizhi.lq.controller;


import com.baizhi.lq.entity.Article;
import com.baizhi.lq.service.ArticleService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @RequestMapping("queryAllArticle")
    public Map queryAllArticle(Integer page, Integer rows) {
        HashMap<String, Object> map = articleService.queryAllPage(page, rows);
        return map;
    }

    @RequestMapping("save")
    public String saveArticle(Article article, String oper) {
        if ("add".equals(oper)) {

            articleService.addArticle(article);
        } else if ("edit".equals(oper)) {
            if (article.getImg() == "") {
                article.setImg(null);
                articleService.updateArticle(article);
            } else {
                articleService.updateArticle(article);
            }
        } else {
            articleService.deleteArticle(article.getId());
        }
        return article.getId();
    }

    @RequestMapping("articleUpload")
    public Map articleUpload(MultipartFile imgFile, String id, HttpServletRequest request) {
        Map map = articleService.articleUpload(imgFile, id, request);
        return map;
    }


    @RequestMapping("showAllImg")
    public Map showAllImg(HttpServletRequest request, HttpSession session) {
        HashMap hashMap = new HashMap();
        hashMap.put("current_url", request.getContextPath() + "/upload/article/");
        String realPath = session.getServletContext().getRealPath("/upload/article/");
        File file = new File(realPath);
        File[] files = file.listFiles();
        hashMap.put("total_count", files.length);
        ArrayList arrayList = new ArrayList();
        for (File file1 : files) {
            HashMap fileMap = new HashMap();
            fileMap.put("is_dir", false);
            fileMap.put("has_file", false);
            fileMap.put("filesize", file1.length());
            fileMap.put("is_photo", true);
            String name = file1.getName();
            String extension = FilenameUtils.getExtension(name);
            fileMap.put("filetype", extension);
            fileMap.put("filename", name);
            String time = name.split("-")[0];
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(new Date(Long.valueOf(time)));
            fileMap.put("datetime", format);
            arrayList.add(fileMap);

        }
        hashMap.put("file_list", arrayList);
        return hashMap;

    }

    @RequestMapping("articleUploa")
    public void articleUploa(MultipartFile inputfile, String id, HttpServletRequest request) {
        articleService.articleUploa(inputfile, id, request);
    }


    @RequestMapping("saveArticle")
    public String saveArticle(Article article, MultipartFile inputfile, String id, HttpServletRequest request) {
        System.out.println(inputfile);
        if (article.getId() == null || "".equals(article.getId())) {
            //添加
            System.out.println(article + "控制层");
            String uri = articleService.articleUploa(inputfile, id, request);
            article.setImg(uri);
            articleService.addArticle(article);

        } else {
            //修改
            if (article.getImg() == "") {
                article.setImg(null);
                articleService.updateArticle(article);
            } else {
                String uri = articleService.articleUploa(inputfile, id, request);
                article.setImg(uri);
                articleService.updateArticle(article);
            }

        }

        return article.getId();
    }
}
