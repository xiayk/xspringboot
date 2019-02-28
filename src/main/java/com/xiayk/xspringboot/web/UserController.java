package com.xiayk.xspringboot.web;

import com.xiayk.xspringboot.model.ret.RetResponse;
import com.xiayk.xspringboot.model.ret.RetResult;
import com.xiayk.xspringboot.model.user.User;
import com.xiayk.xspringboot.service.UserService;
import com.xiayk.xspringboot.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    /**
     * 定向个人信息页
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/{username}")
    public String userIndex(Model model, HttpServletRequest request, @PathVariable String username){
        String uname = ((User)request.getSession().getAttribute("user")).getUsername();
        if (!username.equals(uname)){
            username = uname;
        }
        User user = userService.findUserByUsername(username);
        model.addAttribute("user", user);
        logger.info("定向个人信息页 ======= " + user.getUsername());
        return "user/Info";
    }

//    @RequestMapping(value = "/Info")
//    public String userInfo(HttpServletRequest request, Model model){
//        User user = (User)request.getSession().getAttribute("user");
//        model.addAttribute("user",user);
//        return "user/Info";
//    }

    /**
     * 定向设置页
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/Setting")
    public String userSetting(Model model, HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        model.addAttribute("user", user);
        return "user/Setting";
    }

    /**
     * 上传头像
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload")
    public @ResponseBody Map<String, Object> user(@RequestParam("file") MultipartFile file,
                                                  HttpServletRequest request, @RequestParam String username) {
        Map<String, Object> map = new HashMap<>();
        User sessionUser = (User) request.getSession().getAttribute("user");
        User user = new User();
        //判断是否为当前登录账户
        if (username.equals(sessionUser.getUsername())){
            user = userService.findUserByUsername(username);
        }else {
            //查看login用户权限
            if (sessionUser.getRole().equals("user")){
                map.put("code",-1);
                map.put("msg","当前用户异常错误");
                return map;
            }else if(sessionUser.getRole().equals("admin")){
                //admin === 查找username信息
                if (sessionUser == null){
                    map.put("code",-1);
                    map.put("msg","当前用户异常错误");
                    return map;
                }
                user.setUsername(username);
            }
        }
        Map<String, Object> m = FileUtil.uploadHeadImg(file,user);
        if ((int)m.get("code")==0){
            logger.info(user.getUsername() + "头像修改 ---- true");
            user.setUsername(user.getUsername());
            user.setHeadurl("/UserheadImg/"+user.getUsername()+".png");
            int a = userService.updateUserByUsername(user);
            //如果为login用户 --- 更新session中信息
            if (username.equals(user.getUsername()) && a > 0){
                logger.info("更新session中"+sessionUser.getUsername()+"的信息");
                request.getSession().removeAttribute("user");
                request.getSession().setAttribute("user",userService.findUserByUsername(sessionUser.getUsername()));
            }
            return m;
        }else {
            logger.info(user.getUsername() + "头像修改 ---- false");
            return m;
        }
    }


    @RequestMapping(value = "/update")
    @ResponseBody
    public RetResult updateUserInfo(@RequestParam User user){
        userService.updateUserByUsername(user);
        return RetResponse.makeRsp(200,user.getUsername()+"的信息已更新");
    }

    @RequestMapping(value = "/newPass")
    @ResponseBody
    public RetResult newPass(HttpServletRequest request, @RequestParam String username, @RequestParam String password){
        User loginUser = (User)request.getSession().getAttribute("user");
        if (loginUser.getRole().equals("admin")){
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            int a = userService.updatePass(user);
            if (a > 0){
                return RetResponse.makeRsp(200,user.getUsername()+"的密码已更新");
            }else {
                return RetResponse.makeRsp(-1,user.getUsername()+"的密码更新失败");
            }
        }else {
            String oldPass = request.getAttribute("oldPass").toString();
            if (password.equals(oldPass)){
                return RetResponse.makeRsp(-1,"新密码不能与旧密码一样");
            }else {
                User u = userService.findUserByUsername(username);
                if (u.getPassword().equals(oldPass)){
                    return RetResponse.makeRsp(-1,"旧密码错误");
                }
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                int a = userService.updatePass(user);
                if (a > 0){
                    return RetResponse.makeRsp(200,user.getUsername()+"的密码已更新");
                }else {
                    return RetResponse.makeRsp(-1,user.getUsername()+"的密码更新失败");
                }
            }
        }
    }
}
