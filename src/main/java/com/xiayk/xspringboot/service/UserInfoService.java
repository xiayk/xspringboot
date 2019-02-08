package com.xiayk.xspringboot.service;

import com.xiayk.xspringboot.model.user.User;
import com.xiayk.xspringboot.model.user.UserInfo;

import java.util.List;

/**
 * @Author: XiaYk
 * @create:2019-01-20
 **/
public interface UserInfoService {

    List<User> getUsers(String page, String limit);

    int deleteByPrimaryKey(Integer uid);

    int insert(User record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
}
