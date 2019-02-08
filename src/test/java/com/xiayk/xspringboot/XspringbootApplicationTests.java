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
        User user = userService.findUserByUsername("admin");
        System.out.println(user.getUid());
    }
}

