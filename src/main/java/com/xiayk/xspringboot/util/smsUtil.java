package com.xiayk.xspringboot.util;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;

import java.io.IOException;

/**
 * @Author: XiaYk
 * @create:2019-02-13
 **/
public class smsUtil {

    // 短信应用SDK AppID
    private static final int APPID = 1400113994;
    // 短信应用SDK AppKey
    private static final String APPKEY = "426faf263436512e3d26ee3aaaaac614";
    // 短信模板ID，需要在短信应用中申请
    private static int templateId = 0; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
    //templateId7839对应的内容是"您的验证码是: {1}"
    // 签名
    private static String smsSign = "By - xiayk.com"; // NOTE:


    /**
     * 发送短信验证
     * @param phoneNumbers
     * @param type
     * @return
     */
    public static String getSms(String[] phoneNumbers, String type){
        if (type.equals("newUser")){
            templateId = 160363;
        }else{
            templateId = 277674;
        }
        String num = getNum();
        try {
            String[] params = {num, "3"};//数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
            SmsSingleSender ssender = new SmsSingleSender(APPID, APPKEY);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],
                    templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            System.out.println(result);
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 获取随机数
     * @return
     */
    public static String getNum(){
        int num = (int)(Math.random() * 1000000);
        return ""+num;
    }
//
//    public static void main(String[] args){
//        String[] a = {"17752717248"};
//        System.out.println(getSms(a, "login"));
//    }

}
