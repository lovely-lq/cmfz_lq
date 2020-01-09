package com.baizhi.lq.dao;

import com.baizhi.lq.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumDao {
    //查所有
    public List<Album> queryByPage(@Param("start") Integer start, @Param("rows") Integer rows);

    //总条数
    public int queryCount();

    //添加
    public void addAblum(Album album);

    //修改
    public void updateAlbum(Album album);

    //删除
    public void deleteAlbum(String id);

    //查所有
    public Album selectByIdAlbum(String id);

    //随机两条
    public List<Album> selectByDate();
}
