package com.xiayk.xspringboot.service;

import io.github.biezhi.wechat.WeChatBot;
import io.github.biezhi.wechat.api.annotation.Bind;
import io.github.biezhi.wechat.api.constant.Config;
import io.github.biezhi.wechat.api.enums.MsgType;
import io.github.biezhi.wechat.api.model.WeChatMessage;
import io.github.biezhi.wechat.utils.StringUtils;

/**
 * @Author: XiaYk
 * @create:2019-01-20
 **/
public class WechatService{// extends WeChatBot {

//    public WechatService(Config config){
//        super(config);
//    }
//
//    @Bind(msgType = MsgType.ALL)
//    public void handleText(WeChatMessage message){
//        if (StringUtils.isNotEmpty(message.getName())){
//            System.out.println("接收到的消息:"+message.getName()+":"+message.getText());
//            this.sendMsg(message.getFromUserName(),"自动回复:"+message.getText());
//        }
//    }
//
//    public static void main(String [] args) {
//        new WechatService(Config.me().autoLogin(true).showTerminal(true)).start();
//    }
}
