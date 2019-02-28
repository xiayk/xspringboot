package com.xiayk.xspringboot.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiayk.xspringboot.config.MailModel;
import com.xiayk.xspringboot.model.ret.RetResponse;
import com.xiayk.xspringboot.model.ret.RetResult;
import com.xiayk.xspringboot.model.user.User;
import com.xiayk.xspringboot.service.MailService;
import com.xiayk.xspringboot.service.UserInfoService;
import com.xiayk.xspringboot.service.UserService;
import com.xiayk.xspringboot.util.FileUtil;
import com.xiayk.xspringboot.util.MailUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    MailService mailService;

    @RequestMapping(value = "getUsers")
    @ResponseBody
    public RetResult getUser(String page, String limit, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(limit));
        PageInfo list = userService.getAllUser();//getUsersLimit(page, limit);
        return RetResponse.makeRsp(0,"res:ok",list.getList(),userService.getUsers().size());
    }

    @RequestMapping(value = "index")
    public String getUsers(){
        return "admin/index";
    }

    @RequestMapping(value = "delUser", method = RequestMethod.POST)
    @ResponseBody
    public RetResult delUser(@RequestParam String uid, HttpServletRequest request){
        logger.info("正在删除："+uid+"的数据");
        User user = (User) request.getSession().getAttribute("user");
        User delUser = userService.findUserByUid(Integer.parseInt(uid));
        delUser.setUstatus(0);
        int a = userService.updateUserByUsername(delUser);
        //int a = userService.delUserByUid(Integer.parseInt(uid));
        if (a > 0) {
            String [] to = {"xiayk@xiayk.cn","admin@admin.cn"};
            mailService.sendSimpleEmail(to,"xWeb删除用户", MailModel.getStr(user.getUsername(),delUser.getUsername(),"删除"));
            logger.info(((User) SecurityUtils.getSubject().getSession().getAttribute("user")).getUsername() + "删除了 " + a + " 条的数据");
            return RetResponse.makeRsp(200,"true");
        }
        return RetResponse.makeRsp(-1,"false");
    }

    /**
     * 获取用户信息
     * @param request
     * @return
     */
    @RequestMapping(value = "user", method = RequestMethod.GET)
    public String getUser(HttpServletRequest request, @RequestParam String username){

        logger.info("获取"+username+"的信息");
        User user = userService.findUserByUsername(username);
        request.setAttribute("user", user);
        return "user/Info";
    }

    @RequestMapping(value = "unLock",method = RequestMethod.POST)
    @ResponseBody
    public RetResult unLock(@RequestParam String isLock, @RequestParam String uid, HttpServletRequest request){
        User user = userService.findUserByUid(Integer.parseInt(uid));
        user.setUcode(isLock);
        int a = userService.updateUserByUsername(user);
        if (a > 0){
            if (Boolean.parseBoolean(userService.findUserByUid(Integer.parseInt(uid)).getUcode())){
                logger.info(((User)request.getSession().getAttribute("user")).getUsername()+" 锁定了账号:"+user.getUsername());
                return RetResponse.makeRsp(200,user.getUsername() + "已锁定");
            }else {
                logger.info(((User)request.getSession().getAttribute("user")).getUsername()+" 解锁了账号:"+user.getUsername());
                return RetResponse.makeRsp(200,user.getUsername() + "已解锁");
            }
        }else{
            return RetResponse.makeRsp(-1,"未知错误");
        }
    }

    @RequestMapping(value = "users")
    public String users(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        request.setAttribute("user", user);
        return "admin/index";
    }
}
