package com.baizhi.lq.controller;

import com.baizhi.lq.entity.Banner;
import com.baizhi.lq.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    BannerService bannerService;

    @RequestMapping("/queryByPage")
    public Map<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = bannerService.showAllPage(page, rows);
        return map;

    }

    @RequestMapping("/save")
    public String savePer(Banner b, String oper) {
        if ("add".equals(oper)) {
            bannerService.addBanner(b);
        } else if ("edit".equals(oper)) {
            if (b.getUrl() == "") {
                b.setUrl(null);
                bannerService.updateBanner(b);
            } else {
                bannerService.updateBanner(b);
            }

        } else {
            bannerService.deleteBanner(b.getId());
        }
        return b.getId();
    }

    @RequestMapping("/uploadBanner")
    public void uploadBanner(MultipartFile url, String id, HttpServletRequest request) {
        bannerService.bannerUpload(url, id, request);
    }
}
