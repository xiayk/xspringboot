package com.xiayk.xspringboot.web;

import com.xiayk.xspringboot.model.user.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/notLogin")
    public String noLogin(Model model){
        return "login";
    }

    @RequestMapping(value = "/logOut")
    public String logOut(){
        Subject subject = SecurityUtils.getSubject();
        logger.info("用户注销:-------" + ((User)subject.getSession().getAttribute("user")).getPassword());
        subject.logout();
        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index1(Model model, HttpServletRequest request){
        if (SecurityUtils.getSubject().getSession().getAttribute("user")!=null){
            return "index";
        }
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request){
        return "login";
    }

    @RequestMapping(value = "/notRole")
    @ResponseBody
    public String notRole(){
        return "notRole";
    }
}
