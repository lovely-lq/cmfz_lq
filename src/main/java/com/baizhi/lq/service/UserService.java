package com.baizhi.lq.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    public void UserImgUpload(MultipartFile photo, HttpServletRequest request, String id);
}
