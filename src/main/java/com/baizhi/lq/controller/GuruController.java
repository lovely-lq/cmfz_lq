package com.baizhi.lq.controller;

import com.baizhi.lq.entity.Guru;
import com.baizhi.lq.service.GuruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("guru")
public class GuruController {
    @Autowired
    GuruService guruService;

    @RequestMapping("queryAll")
    public List<Guru> queryAll() {
        List<Guru> gurus = guruService.queryAll();
        return gurus;
    }
}
