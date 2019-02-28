package com.xiayk.xspringboot.model.user;

/**
 * @Author: XiaYk
 * @create:2019-02-23
 **/
public class Role {
    private Integer rid;
    private String name;
    private Integer rolestatus;
    private String rname;
    private String roleDesc;
    /**
     * 权限
     */
    private String permissions;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    public Integer getRolestatus() {
        return rolestatus;
    }

    public void setRolestatus(Integer rolestatus) {
        this.rolestatus = rolestatus;
    }

    public Integer getRid() {
        return rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRname() {
        return rname;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getPermissions() {
        return permissions;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
