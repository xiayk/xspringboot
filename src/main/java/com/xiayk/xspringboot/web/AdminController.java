package com.xiayk.xspringboot.web;

import com.xiayk.xspringboot.model.ret.RetResponse;
import com.xiayk.xspringboot.model.ret.RetResult;
import com.xiayk.xspringboot.model.user.User;
import com.xiayk.xspringboot.service.UserInfoService;
import com.xiayk.xspringboot.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/")
public class AdminController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @Autowired
    UserInfoService userInfoService;

    @RequestMapping(value = "getUsers")
    @ResponseBody
    public RetResult getUser(String page, String limit, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, String> map = new HashMap<>();
        List<User> list = userInfoService.getUsers(page, limit);
        return RetResponse.makeRsp(0,"res:ok",list,userService.getUserConut());
    }

    @RequestMapping(value = "index")
    public String getUsers(){
        return "admin/index";
    }

    @RequestMapping(value = "delUser", method = RequestMethod.POST)
    @ResponseBody
    public RetResult delUser(@RequestParam String uid){
        logger.info("正在删除："+uid+"的数据");
        int b = userInfoService.deleteByPrimaryKey(Integer.parseInt(uid));
        if (b > 0){
            int a = userService.deleteByPrimaryKey(Integer.parseInt(uid));
            if (a > 0)
                logger.info("删除"+ b +"条个人信息," + a + "条的数据");
                return RetResponse.makeRsp(200,"res:ok");
        }else {
            return RetResponse.makeRsp(-1,"res:error");
        }
    }

    @RequestMapping(value = "unLock",method = RequestMethod.POST)
    @ResponseBody
    public RetResult unLock(@RequestParam String isLock, HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        user.setUcode(isLock);
        logger.info("正在锁定账号:"+user.getUsername());
        int a = userService.updateByPrimaryKey(user);
        if (a > 0){
            return RetResponse.makeRsp(200,user.getUsername() + "已锁定");
        }else{
            return RetResponse.makeRsp(-1,"未知错误");
        }
    }

    @RequestMapping(value = "user")
    public String user(){
        return "admin/index";
    }


}
