package com.baizhi.lq.dao;

import com.baizhi.lq.entity.Guru;

import java.util.List;

public interface GuruDao {
    //查所有
    public List<Guru> queryAll();

    public void insertGuru(Guru guru);
}
