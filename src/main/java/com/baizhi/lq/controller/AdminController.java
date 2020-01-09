package com.baizhi.lq.controller;


import com.baizhi.lq.entity.Admin;
import com.baizhi.lq.service.AdminService;
import com.baizhi.lq.util.CreateValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @RequestMapping("/login")
    @ResponseBody
    public Map login(Admin admin, HttpSession session, String clientCode) {
        String serverCode = (String) session.getAttribute("ServerCode");
        HashMap hashMap = new HashMap();
        // String emg=null;
        Admin admin1 = adminService.selectByAdmin(admin);
        //登录失败
        if (serverCode.equals(clientCode)) {
            if (admin1 == null) {
                hashMap.put("status", 400);
                hashMap.put("msg", "该用户不存在");
            } else if (!admin1.getPassword().equals(admin.getPassword())) {
                hashMap.put("status", 400);
                hashMap.put("msg", "密码错误");
            } else {
                //登陆成功
                session.setAttribute("admin", admin1);
                hashMap.put("status", 200);
            }
        } else {
            hashMap.put("status", 400);
            hashMap.put("msg", "验证码错误");
        }

        return hashMap;
    }

    // 生成验证码图片
    @RequestMapping("/createImg")
    @ResponseBody
    public void createImg(HttpServletResponse response, HttpSession session) throws IOException {
        CreateValidateCode vcode = new CreateValidateCode();
        String code = vcode.getCode(); // 随机验证码
        vcode.write(response.getOutputStream()); // 把图片输出client

        // 把生成的验证码 存入session
        session.setAttribute("ServerCode", code);
    }

    @RequestMapping("loginOut")
    //安全退出
    public String loginOut(HttpSession session) {
        session.removeAttribute("admin");
        session.invalidate();
        return "redirect:/jsp/login.jsp";
    }

}
