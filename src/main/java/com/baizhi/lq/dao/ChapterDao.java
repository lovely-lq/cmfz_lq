package com.baizhi.lq.dao;

import com.baizhi.lq.entity.Chapter;

import java.util.List;

public interface ChapterDao {
    //查单个
    public List<Chapter> queryById(String id);

    //添加
    public void addChapter(Chapter chapter);

    //修改
    public void updateChapter(Chapter chapter);

    //删除
    public void deleteChapter(String id);

    //通过专辑id
    public List<Chapter> selectAllChapter(String id);
}
