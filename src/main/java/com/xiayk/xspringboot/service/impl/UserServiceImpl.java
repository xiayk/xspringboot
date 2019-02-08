package com.xiayk.xspringboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.xiayk.xspringboot.dao.UserInfoMapper;
import com.xiayk.xspringboot.dao.UserMapper;
import com.xiayk.xspringboot.model.user.User;
import com.xiayk.xspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public int getUserConut() {
        return userMapper.getUserConut();
    }

    @Override
    public List<User> findUsers(String pageNo, String pageSize) {
        PageHelper.startPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
        return userMapper.findUsers();
    }

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public int deleteByPrimaryKey(Integer uid) {
        return userMapper.deleteByPrimaryKey(uid);
    }

    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }


    @Override
    public User selectByPrimaryKey(Integer uid) {
        return userMapper.selectByPrimaryKey(uid);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int insert(User record) {
        return userInfoMapper.insert(record);//添加用户信息
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

}
