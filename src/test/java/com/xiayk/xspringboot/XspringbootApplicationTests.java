package com.xiayk.xspringboot;

import com.xiayk.xspringboot.dao.UserInfoMapper;
import com.xiayk.xspringboot.model.user.User;
import com.xiayk.xspringboot.model.user.UserInfo;
import com.xiayk.xspringboot.service.UserInfoService;
import com.xiayk.xspringboot.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XspringbootApplicationTests {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    UserService userService;

    @Test
    public void contextLoads() {
        //User user = userService.findUserByUsername("admin");
        User user = new User();
        user.setUcode("true");
        user.setUsername("user");
        user.setSex("男");
        user.setRole("user");
        user.setNickname("TOMHEI");
        user.setEmail("22249141@xiayk.com");
        user.setAddres("湖南-长沙");
        user.setPhoneNum("110");
        user.setPassword("111");
        user.setJob("运维");
        user.setOther("无备注");
        System.out.println(userService.insert(user));
    }
}

