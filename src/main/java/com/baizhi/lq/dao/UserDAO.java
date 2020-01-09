package com.baizhi.lq.dao;

import com.baizhi.lq.entity.User;
import com.baizhi.lq.entity.UserDto;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDAO extends Mapper<User> {
    //按时间和性别查询用户活跃度
    public Integer selectUserCount(@Param("sex") String sex, @Param("day") Integer day);

    //按性别和地区查
    public List<UserDto> selectByLocation(String sex);

    //添加
    public void updateUserMessage(User u);

    //金刚道友
    public List<User> selectByNear(String id);
}
