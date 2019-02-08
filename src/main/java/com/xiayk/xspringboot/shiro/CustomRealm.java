package com.xiayk.xspringboot.shiro;

import com.xiayk.xspringboot.model.user.User;
import com.xiayk.xspringboot.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cdyoue on 2016/10/21.
 */
@Component
public class CustomRealm extends AuthorizingRealm {
    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;
//    @Autowired
//    private PermissionDao permissionService;

    /**
     * 获取授权信息
     * @param principalCollection
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) throws AuthenticationException {
        logger.info("权限认证:+"+principalCollection.toString());
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userService.findUserByUsername((String) principalCollection.getPrimaryPrincipal());
        logger.info("principalCollection:"+(String) principalCollection.getPrimaryPrincipal()+"--------  SecurityUtils:" +username);
        String role = userService.findUserByUsername(username).getRole();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> set = new HashSet<>();
        //需要将 role 封装到 Set 作为 info.setRoles() 的参数
        set.add(role);
        //设置该用户拥有的角色
        info.setRoles(set);
        //把principals放session中 key=userId value=principals
        SecurityUtils.getSubject().getSession().setAttribute(String.valueOf(user.getUid()),SecurityUtils.getSubject().getPrincipals());
        return info;
    }

    /**
     * 获取身份验证信息
     * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的
     *
     * @param authenticationToken authenticationToken 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("身份认证方法: +"  + authenticationToken.toString());

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName=token.getUsername();
        logger.info(userName+token.getPassword());

        User user = userService.findUserByUsername(token.getUsername());

        if (user == null)
            throw new AccountException("用户名不正确");
        else if (!user.getPassword().equals(new String((char[]) token.getCredentials())))
            throw new AccountException("密码不正确");
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("user", user);
        return new SimpleAuthenticationInfo(userName,user.getPassword(),getName());
    }
}
