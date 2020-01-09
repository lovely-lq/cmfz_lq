package com.baizhi.lq.dao;

import com.baizhi.lq.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleDao {
    //查所有
    public List<Article> queryByPage(@Param("start") Integer start, @Param("rows") Integer rows);

    //总条数
    public int queryCount();

    //添加
    public void addArticle(Article article);

    //修改
    public void updateArticle(Article article);

    //删除
    public void deleteArticle(String id);

    //通过id查询文章
    public Article selectByOne(String id);

    public List<Article> selectArticleDate();
}
