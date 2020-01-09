package com.baizhi.lq.controller;

import com.baizhi.lq.entity.Album;
import com.baizhi.lq.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("album")
public class AlbumController {

    @Autowired
    AlbumService albumService;


    @RequestMapping("queryByPage")
    public Map<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = albumService.queryAllPage(page, rows);
        return map;
    }

    @RequestMapping("save")
    public String saveAlbum(Album a, String oper) {
        if ("add".equals(oper)) {
            albumService.addAblum(a);
        } else if ("edit".equals(oper)) {
            if (a.getHeadSrc() == "") {
                a.setHeadSrc(null);
                albumService.updateAlbum(a);
            } else {
                albumService.updateAlbum(a);
            }

        } else {
            albumService.deleteAlbum(a.getId());
        }
        return a.getId();
    }

    @RequestMapping("/uploadalbum")
    public void uploadalbum(MultipartFile headSrc, String id, HttpServletRequest request) {
        albumService.albumUpload(headSrc, id, request);
    }
}
