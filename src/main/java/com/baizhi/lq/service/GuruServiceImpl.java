package com.baizhi.lq.service;

import com.baizhi.lq.dao.GuruDao;
import com.baizhi.lq.entity.Guru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuruServiceImpl implements GuruService {
    @Autowired
    GuruDao guruDao;


    @Override
    public List<Guru> queryAll() {
        List<Guru> gurus = guruDao.queryAll();
        return gurus;
    }
}
