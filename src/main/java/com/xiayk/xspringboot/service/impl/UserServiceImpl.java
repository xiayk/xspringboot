package com.xiayk.xspringboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiayk.xspringboot.dao.UserInfoMapper;
import com.xiayk.xspringboot.dao.UserMapper;
import com.xiayk.xspringboot.model.user.User;
import com.xiayk.xspringboot.service.UserService;
import com.xiayk.xspringboot.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByUid(Integer uid) {
        return userMapper.findUserByUid(uid);
    }

    @Override
    public int delUserByUsername(String username) {
        return userMapper.delUserByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        return userMapper.getUsers();
    }

    @Override
    public List<User> getUsersLimit(String pageNo, String pageSize) {
        Map<String, Integer> map = new HashMap<>();
        map.put("page", Integer.parseInt(pageNo)-1);
        map.put("limit", Integer.parseInt(pageSize));
        return userMapper.getUsersLimit(map);
    }

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public int delUserByUid(Integer uid) {
        return userMapper.delUserByUid(uid);
    }
    @Override
    public int insert(User record) {
        if (userMapper.findUserByUsername(record.getUsername())==null){
            record.setRegTime(FileUtil.getDate("time"));
            userMapper.insert(record);
            return userMapper.updateUserByUsername(record);
        }
        return -1;
    }

    @Override
    public User findUserByPhoneNumber(String phoneNum) {
        return userMapper.findUserByPhoneNumber(phoneNum);
    }

    @Override
    public int updateUserByUsername(User record) {
        return userMapper.updateUserByUsername(record);
    }

    @Override
    public int updatePass(User record) {
        return userMapper.updatePass(record);
    }

    @Override
    public PageInfo<User> getAllUser() {
        List<User> list = userMapper.getAllUser();
        PageInfo<User> p=new PageInfo<User>(list);
        return p;
    }
}
