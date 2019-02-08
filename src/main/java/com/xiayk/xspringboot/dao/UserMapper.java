package com.xiayk.xspringboot.dao;

import com.xiayk.xspringboot.model.user.User;

import java.util.List;

public interface UserMapper {

    int getUserConut();

    List<User> findUsers();

    User findUserByUsername(String username);

    int deleteByPrimaryKey(Integer uid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}