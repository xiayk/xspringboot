package com.xiayk.xspringboot.util;

import com.xiayk.xspringboot.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: XiaYk
 * @create:2019-02-13
 **/
public class FileUtil {

    /**
     * 上传头像
     * @param file
     * @return
     */
    public static Map<String, Object> uploadHeadImg(MultipartFile file, User user) {
        Map<String, Object> map = new HashMap<>();
        if (!file.isEmpty()) {
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(
                                "src/main/resources/static/UserheadImg/" + user.getUsername()+".png")));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                map.put("updateHeadcode",-1);
                map.put("msg",e.getMessage());
                return map;
            } catch (IOException e) {
                e.printStackTrace();
                map.put("updateHeadcode",-1);
                map.put("msg",e.getMessage());
                return map;
            }
            map.put("code",0);
            map.put("msg", "ok");
            map.put("headurl", "/UserheadImg/"+user.getUsername()+".png");
            return map;
        } else {
            map.put("code",-1);
            map.put("msg","上传失败，因为文件是空的.");
            return map;
        }
    }
    /**
     * 获取url文件
     * @param httpUrl
     * @return
     */
    public static Map<String ,Object> getUrlFile(String httpUrl){
        String pathname = null;
        Map<String, Object> map = new HashMap<>();
        try {
            //new一个URL对象
            URL url = new URL(httpUrl);
            //截取文件后缀
            String fileType = url.getFile().substring(url.getFile().lastIndexOf("."));
            //打开链接
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //文件名
            pathname = "xWEB"+getDate("name")+ "_" + Math.random() + fileType;
            //文件大小
            String fileSize = conn.getContentLength()/1000+"kb";
            //超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);
            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] data = readInputStream(inStream);
            //new一个文件对象用来保存图片，默认保存当前工程根目录
            File imageFile = new File("src/main/resources/static/upload/" + pathname);
            //创建输出流
            FileOutputStream outStream = new FileOutputStream(imageFile);
            //写入数据
            outStream.write(data);
            //关闭输出流
            outStream.close();
        }catch (IOException e){
            map.put("code", e.hashCode());
            map.put("res", e.getMessage());
            return map;
        }catch (Exception e){
            map.put("code", e.hashCode());
            map.put("res", e.getMessage());
        }
        map.put("code", 0);
        map.put("res", "下载完成");
        map.put("FileName", pathname);
        return map;
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    public static String getDate(String type){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = null;
        if (type.equals("name")){
            simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HH");
        }else if (type.equals("time")){
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        String dd = simpleDateFormat.format(date);
        return dd;
    }
}
