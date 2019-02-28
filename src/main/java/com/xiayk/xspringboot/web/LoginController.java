package com.xiayk.xspringboot.web;

import com.xiayk.xspringboot.model.ret.RetResponse;
import com.xiayk.xspringboot.model.ret.RetResult;
import com.xiayk.xspringboot.model.user.User;
import com.xiayk.xspringboot.service.UserService;
import com.xiayk.xspringboot.util.smsUtil;
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

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public RetResult<User> login(@RequestParam String username, @RequestParam String password, HttpServletRequest request, @RequestParam String loginType){
        if (request.getSession().getAttribute("user") != null)
            return RetResponse.makeRsp(-1, "请注销当前账号后重试");

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = null;
        if("Num".equals(loginType)){
            if (!username.equals(request.getSession().getAttribute("smsPhoneNum"))){
                return RetResponse.makeRsp(-1, "手机号与验证码不匹配");
            }
            User user;
            try {
                user = userService.findUserByPhoneNumber(username);
            }catch (Exception e){
                return RetResponse.makeRsp(-1, "手机号码异常,请使用账号登录");
            }
            String num = (String)request.getSession().getAttribute("smsCode");
            if (!password.equals(num)){
                return RetResponse.makeRsp(-1,"验证码错误!");
            }
            if (user!= null){
                if (user.getUcode().equals("true")){
                    return RetResponse.makeRsp(-1, "当前账号已被锁定","/",null);
                }
                token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
                subject.login(token);
            }else {
                return RetResponse.makeRsp(-1, "手机号不存在");
            }
        }else {
            if(username==null || "".equals(username)){
                return RetResponse.makeErrRsp("username is null");
            }
            if(password==null || "".equals(password)){
                return RetResponse.makeErrRsp("password is null");
            }
            User user = userService.findUserByUsername(username);
            if (user == null){
                return RetResponse.makeRsp(-1, "用户名不存在","/",null);
            }
            if("true".equals(user.getUcode())){
                return RetResponse.makeRsp(-1, "当前账号已被锁定","/",null);
            }else {
                token = new UsernamePasswordToken(username,password);
                logger.info(username + "--> loging");
            }
        }
        try {
            subject.login(token);
            logger.info(username + " ---- login --> true --->subject.isAuthenticated() = "+subject.isAuthenticated());
            return RetResponse.makeRsp(100, "登陆成功！","/",null);
        } catch (AuthenticationException e) {
            logger.info(username + " ---- login --> false");
            return RetResponse.makeErrRsp("用户名或密码错误!");
        }
    }

    @RequestMapping(value = "/login/getSmsNum",method = RequestMethod.GET)
    @ResponseBody
    public RetResult getNum(@RequestParam String phoneNumber, HttpServletRequest request){
        String[] phoneNumbers = {phoneNumber};
        try {
            getSmsTask task = new getSmsTask(phoneNumbers);
            task.run();
            request.getSession().setAttribute("smsCode",task.getSmsNum());
            request.getSession().setAttribute("smsPhoneNum", phoneNumber);
            return RetResponse.makeRsp(200,"验证码发送成功!");
        }catch (Exception e){
            return RetResponse.makeRsp(-1,"验证码发送失败!");
        }
    }

    private class getSmsTask implements Runnable{
        private String[] phoneNumbers;
        private String SmsNum;

        public String getSmsNum() {
            return SmsNum;
        }

        public void setSmsNum(String smsNum) {
            SmsNum = smsNum;
        }

        public getSmsTask(String[] phoneNumbers) {
            this.phoneNumbers = phoneNumbers;
        }

        @Override
        public void run() {
            String a = smsUtil.getSms(phoneNumbers,"login");
            setSmsNum(a);
            logger.info("验证码:"+a);
        }
    }

    @RequestMapping(value = "register")
    public String addUserInfo(){
        return "register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public RetResult addUserInfo(User user, String username, String password, HttpServletRequest request){
        logger.info(username+" 注册");
        user.setIp(request.getLocalAddr());
        user.setPassword(password);
        user.setNickname("xWeb_"+username);
        user.setUsername(username);
        user.setUcode("false");
        user.setRole("user");
        int a = userService.insert(user);
        if (a >= 0)
            return RetResponse.makeRsp(200,"注册成功!","/login",null);
        else if (a==-1)
            return RetResponse.makeRsp(-1,"用户名重复",null);
        return RetResponse.makeRsp(400,"未知错误导致注册失败");
    }

    @RequestMapping(value = "/logOut", method = RequestMethod.POST)
    @ResponseBody
    public RetResult logOut(HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        logger.info("logout() ---> isHaveSession>>>>>>>" + (subject.getSession().getAttribute("user")==null?false:true));
        return RetResponse.makeRsp(200,"注销成功!");
    }

    @RequestMapping(value = "/isName", method = RequestMethod.POST)
    public RetResult isUsername(@RequestParam String username){
        User user = userService.findUserByUsername(username);
        if (user == null){
            return RetResponse.makeRsp(1,"");
        }else{
            return RetResponse.makeRsp(-1,"用户名重复");
        }
    }
}
