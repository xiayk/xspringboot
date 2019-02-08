package com.xiayk.xspringboot.dao;

import com.xiayk.xspringboot.model.user.User;
import com.xiayk.xspringboot.model.user.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserInfoMapper {
    List<User> getUsers(Map<String, Integer> map);

    int deleteByPrimaryKey(Integer uid);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
}