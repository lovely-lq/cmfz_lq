package com.baizhi.lq.dao;

import com.baizhi.lq.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerDao {
    //start : 起始页     rows：每页显示的条数
    public List<Banner> findByPage(@Param("start") Integer start, @Param("rows") Integer rows);

    //总条数
    public Integer totalCount();

    //添加
    public void add(Banner b);

    //删除
    public void delete(String id);

    //修改
    public void update(Banner b);

    //查所有
    public List<Banner> selectAll();


    public List<Banner> selectAllBanner();
}
