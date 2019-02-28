package com.xiayk.xspringboot.service;

import com.github.pagehelper.PageInfo;
import com.xiayk.xspringboot.model.user.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User findUserByPhoneNumber(String phoneNum);
    /**
     * 通过uid查找用户
     * @param uid
     * @return
     */
    User findUserByUid(Integer uid);

    /**
     * 获取所有用户 及信息
     * @return
     */
    List<User> getUsers();

    /**
     * 通过用户名查找用户(登录)
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    /**
     * 分页获取数据
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<User> getUsersLimit(String pageNo, String pageSize);

    /**
     * 通过uid删除用户
     * @param uid
     * @return
     */
    int delUserByUid(Integer uid);

    /**
     * 通过username删除用户
     * @param username
     * @return
     */
    int delUserByUsername(String username);

    /**
     * 添加用户
     * @param record
     * @return
     */
    int insert(User record);

    /**
     * 修改用户信息
     * @param record
     * @return
     */
    int updateUserByUsername(User record);

    /**
     * 修改密码
     * @param record
     * @return
     */
    int updatePass(User record);


    PageInfo<User> getAllUser();
}
