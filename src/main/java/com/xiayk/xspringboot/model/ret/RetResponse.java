package com.xiayk.xspringboot.model.ret;

/**
 * 将返回结果转换后的对象
 */
public class RetResponse {

    private final static String SUCCESS = "success";

    public static <T>  RetResult<T> makeOKRsp(){
        return new RetResult<T>().setCode(RetCode.SUCCESS).setMsg(SUCCESS);
    }

    public static <T>  RetResult<T> makeOKRsp(T data){
        return new RetResult<T>().setCode(RetCode.SUCCESS).setMsg(SUCCESS).setData(data);
    }

    public static <T> RetResult<T> makeErrRsp(String message){
        return new RetResult<T>().setCode(RetCode.FALL).setMsg(message);
    }

    public static <T> RetResult<T> makeRsp(int code, String msg){
        return new RetResult<T>().setCode(code).setMsg(msg);
    }

    public static <T> RetResult<T> makeRsp(int code, String msg, T data){
        return new RetResult<T>().setCode(code).setMsg(msg).setData(data);
    }

    public static <T> RetResult<T> makeRsp(int code, String msg, T data, int count){
        return new RetResult<T>().setCode(code).setMsg(msg).setData(data).setCount(count);
    }

    public static <T> RetResult<T> makeRsp(int code, String msg, String url, T data){
        return new RetResult<T>().setCode(code).setMsg(msg).setUrl(url).setData(data);
    }
}
