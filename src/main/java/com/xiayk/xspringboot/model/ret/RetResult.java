package com.xiayk.xspringboot.model.ret;

/**
 * 返回对象实体
 * @param <T>
 */
public class RetResult<T> {

    public int code;
    private String msg;
    private T data;
    private String url;
    private int count;

    public int getCount() {
        return count;
    }

    public RetResult setCount(int count) {
        this.count = count;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public RetResult<T> setUrl(String url) {
        this.url = url;
        return this;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public RetResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public RetResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public RetResult<T> setCode(RetCode retCode){
        this.code = retCode.code;
        return this;
    }

    public RetResult<T> setCode(int code) {
        this.code = code;
        return this;
    }
}
