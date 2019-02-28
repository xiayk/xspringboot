package com.xiayk.xspringboot;

import com.xiayk.xspringboot.dao.UserMapper;
import com.xiayk.xspringboot.model.user.User;
import com.xiayk.xspringboot.service.MailService;
import com.xiayk.xspringboot.service.UserInfoService;
import com.xiayk.xspringboot.service.UserService;
import com.xiayk.xspringboot.service.impl.MailServiceImpl;
import com.xiayk.xspringboot.util.FileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XspringbootApplicationTests {

    @Autowired
    UserService userService;

    @Test
    public void contextLoads() {
        //User user = userService.findUserByUsername("admin");
        User user = new User();
        user.setUcode("true");
        user.setSex("男");
        user.setRole("user");
        user.setEmail("22249141@xiayk.com");
        user.setAddres("湖南-长沙");
        user.setPhoneNum("110");
        user.setPassword("111");
        user.setJob("运维");
        user.setRid(2);
        user.setOther("数据填充");
        for (int a = 0; a<=100; a++){
            user.setUsername("user"+a);
            user.setNickname("xWeb_"+user.getUsername());
            userService.insert(user);
        }
        System.out.println(userService.getUsers().size());
    }

    @Test
    public void getAllUserTest(){
        System.out.println(userService.getAllUser());
    }

    @Test
    public void updateTest(){
        User user = new User();
        user.setUsername("user");
        user.setRole("admin");
        userService.updateUserByUsername(user);
    }

    @Test
    public void getDateTest(){
        System.out.println(FileUtil.getUrlFile("http://localhost:8080/UserheadImg/admin.png"));
    }


    @Autowired
    MailService mailService;

    @Test
    public void mailTest(){
        String[] a = {"22249141@qq.com","xiayk@xiayk.cn"};
        domailTack domailTack = new domailTack(a);
        domailTack.run();
    }

    private class domailTack implements Runnable{
        String to[];
        public domailTack(String[] to){
            this.to = to;
        }

        @Override
        public void run() {
            mailService.sendSimpleEmail(to,"测试", "test");
        }
    }
}

