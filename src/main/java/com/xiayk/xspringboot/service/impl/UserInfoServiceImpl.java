package com.xiayk.xspringboot.service.impl;

import com.xiayk.xspringboot.dao.UserInfoMapper;
import com.xiayk.xspringboot.model.user.User;
import com.xiayk.xspringboot.model.user.UserInfo;
import com.xiayk.xspringboot.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: XiaYk
 * @create:2019-01-22
 **/
@Service(value = "userInfoService")
public class UserInfoServiceImpl implements UserInfoService {


    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public List<User> getUsers(String pageNo, String pageSize) {
        //PageHelper.startPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
        Map<String, Integer> map = new HashMap<>();
        map.put("page", Integer.parseInt(pageNo)-1);
        map.put("limit", Integer.parseInt(pageSize));
        return userInfoMapper.getUsers(map);
    }

    @Override
    public int insert(User record) {
        return userInfoMapper.insert(record);
    }

    @Override
    public UserInfo selectByPrimaryKey(Integer uid) {
        return userInfoMapper.selectByPrimaryKey(uid);
    }

    @Override
    public int updateByPrimaryKeySelective(UserInfo record) {
        return userInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int insertSelective(UserInfo record) {
        return userInfoMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserInfo record) {
        return userInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer uid) {
        return userInfoMapper.deleteByPrimaryKey(uid);
    }
}
