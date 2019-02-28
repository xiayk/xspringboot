package com.xiayk.xspringboot.dao;

import com.xiayk.xspringboot.model.user.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: XiaYk
 * @create:2019-02-24
 **/
public interface RoleMapper {

    List<Role> getRoleList();

    List<Role> getRoles();

    int updateRole(Role role);

    Role findRoleById(int rid);

}
