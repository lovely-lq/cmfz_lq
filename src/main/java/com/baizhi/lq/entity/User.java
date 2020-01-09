package com.baizhi.lq.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @ExcelProperty(value = {"用户", "ID"})
    private String id;
    @ExcelProperty(value = {"用户", "手机"})
    private String phone;
    @ExcelProperty(value = {"用户", "密码"})
    private String password;
    @ExcelProperty(value = {"用户", "盐"})
    private String salt;
    @ExcelProperty(value = {"用户", "状态"})
    private String status;
    @ExcelProperty(value = {"用户", "图片"}, converter = UserContenver.class)
    private String photo;
    @ExcelProperty(value = {"用户", "名字"})
    private String name;
    @ExcelProperty(value = {"用户", "昵称"})
    private String nick;
    @ExcelProperty(value = {"用户", "性别"})
    private String sex;
    @ExcelProperty(value = {"用户", "SIGN"})
    private String sign;
    @ExcelProperty(value = {"用户", "地方"})
    private String location;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty(value = {"用户", "注册时间"})
    private Date rigest_date;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty(value = {"用户", "上次登录时间"})
    private Date last_login;


}
