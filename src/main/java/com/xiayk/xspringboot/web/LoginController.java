package com.xiayk.xspringboot.web;


import com.xiayk.xspringboot.model.ret.RetResponse;
import com.xiayk.xspringboot.model.ret.RetResult;
import com.xiayk.xspringboot.model.user.User;
import com.xiayk.xspringboot.service.UserInfoService;
import com.xiayk.xspringboot.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public RetResult<User> login(@RequestParam String username, @RequestParam String password, HttpServletRequest request){
        if (request.getSession().getAttribute("user") != null)
            return null;
        if(username==null || "".equals(username)){
            return RetResponse.makeErrRsp("username is null");
        }
        if(password==null || "".equals(password)){
            return RetResponse.makeErrRsp("password is null");
        }
        User user = userService.findUserByUsername(username);
        if("true".equals(user.getUcode())){
            return RetResponse.makeRsp(-1, "当前账号已被锁定","/",null);
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        logger.info("----------" + username + "--> loging ------ password:" + token.getPassword());
        try {
            subject.login(token);
            logger.info(username + " ---- login --> true --->subject.isAuthenticated() = "+subject.isAuthenticated());
            return RetResponse.makeRsp(100, "登陆成功！","/",null);
        } catch (AuthenticationException e) {
            logger.info(username + " ---- login --> false");
            return RetResponse.makeErrRsp("用户名或密码错误!");
        }
    }

    @RequestMapping(value = "register")
    public String addUserInfo(){
        return "register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public RetResult addUserInfo(User user){
        int a = userService.insert(user);
        User user1 = userService.findUserByUsername(user.getUsername());
        if(a>=0 && user1==null)
            user.setUid(userService.findUserByUsername(user.getUsername()).getUid());
            if (userInfoService.insert(user)>=0)
                return RetResponse.makeRsp(200,"注册成功!","/login",null);
            else
                return RetResponse.makeRsp(200,"注册成功,信息添加失败,请登录后完善个人信息","/user/Info",null);
        //return RetResponse.makeRsp(400,"未知错误导致注册失败");
    }

    @RequestMapping(value = "/logOut", method = RequestMethod.POST)
    @ResponseBody
    public RetResult logOut(HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        logger.info("logout() ---> isHaveSession>>>>>>>" + (subject.getSession().getAttribute("user")==null?false:true));
        return RetResponse.makeRsp(200,"注销成功!");
    }
}
