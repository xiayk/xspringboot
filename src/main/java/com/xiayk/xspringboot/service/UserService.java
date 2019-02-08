package com.xiayk.xspringboot.service;

import com.xiayk.xspringboot.model.user.User;

import java.util.List;

public interface UserService {

    int getUserConut();

    List<User> findUsers(String pageNo, String pageSize);

    User findUserByUsername(String username);

    int deleteByPrimaryKey(Integer uid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
