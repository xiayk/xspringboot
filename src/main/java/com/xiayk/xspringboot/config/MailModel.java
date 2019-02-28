package com.xiayk.xspringboot.config;

import com.xiayk.xspringboot.util.FileUtil;

/**
 * @Author: XiaYk
 * @create:2019-02-23
 **/
public class MailModel {
    enum sType{
        delete,select,update,insert
    }
    private String username;
    private String uname;
    private String type;

    public static String getStr(String username, String uname, String type){

        if (type.equals(sType.delete)){
            type = "删除";
        }else if (type.equals(sType.insert)){
            type = "添加";
        }else if (type.equals(sType.update)){
            type = "修改";
        }
        String str = "admin,您好:\n\t     "+ username + "于"+ FileUtil.getDate("time") + type+"了"+ uname +
                "的用户信息.\n\n\n\nfrom - xWeb\n";

        return str;
    }
}
