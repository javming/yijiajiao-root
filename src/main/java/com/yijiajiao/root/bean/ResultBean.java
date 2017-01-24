package com.yijiajiao.root.bean;

import java.util.UUID;


public class ResultBean {
    private String requestId = UUID.randomUUID().toString().replace("-", "");
    private String httpCode = "200";
    private int code;
    private String message;
    private Object result = "";

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getResult() {
        return result;
    }
    public void setResult(Object result) {
        this.result = result;
    }
    public String getRequestId() {
        return requestId;
    }
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    public String getHttpCode() {
        return httpCode;
    }
    public void setHttpCode(String httpCode) {
        this.httpCode = httpCode;
    }

    /**
    *
    * @param status  异常返回枚举类型
    */
    public void setFailMsg(SystemStatus status) {
        setCode(status.getCode());
        setMessage(status.getStr());
    }

    /**
    *
    * @param
    *
    */
    public void setFailMsg(int code,String str) {
        setCode(code);
        setMessage(str);
    }

    /**
    *
    * @param result  成功返回数据
    */
    public void setSucResult(Object result) {
        setCode(SystemStatus.OK.getCode());
        setMessage(SystemStatus.OK.getStr());
        setResult(result);
    }

    public static ResultBean newInstance(){
        return new ResultBean();
    }

    private static ResultBean newInstance(int code, String message, Object result){
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(code);
        resultBean.setMessage(message);
        resultBean.setResult(result);
        return resultBean;
    }
    public static ResultBean getSucResult(Object result){
        return newInstance(SystemStatus.OK.getCode(),SystemStatus.OK.getStr(),result);
    }
    public static ResultBean getFailResult(int code ,String message){
        return newInstance(code,message,"");
    }
    public static ResultBean getFailResult(SystemStatus status){
        return newInstance(status.getCode(),status.getStr(),"");
    }
}
