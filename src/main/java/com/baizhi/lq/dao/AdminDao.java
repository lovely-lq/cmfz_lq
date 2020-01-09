package com.baizhi.lq.dao;


import com.baizhi.lq.entity.Admin;
import tk.mybatis.mapper.common.Mapper;

public interface AdminDao extends Mapper<Admin> {
    public Admin selectByAdminName(String username, String password);

}
