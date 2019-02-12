package com.xiayk.xspringboot.web;

import com.xiayk.xspringboot.model.ret.RetResponse;
import com.xiayk.xspringboot.model.ret.RetResult;
import com.xiayk.xspringboot.model.user.User;
import com.xiayk.xspringboot.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/")
    public String userIndex(HttpServletRequest request){
        if (((User)request.getSession().getAttribute("user")).getRole().equals("admin"))
            return "admin/index";
        else
            return "user/user";
    }

    @RequestMapping(value = "Info")
    public String userInfo(HttpServletRequest request, Model model){
        User user = (User)request.getSession().getAttribute("user");
        model.addAttribute("user",user);
        return "user/Info";
    }

    @RequestMapping(value = "Setting")
    public String userSetting(Model model, HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        model.addAttribute("userInfo", user);
        return "user/Setting";
    }

    @RequestMapping(value = "{username}")
    public String user(){
        return "user/user";
    }
}
