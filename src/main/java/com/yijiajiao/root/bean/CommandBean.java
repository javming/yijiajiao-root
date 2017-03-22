package com.yijiajiao.root.bean;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-03-17-10:36
 */
public class CommandBean {

    private String cmd;
    private String tag;
    private String token;
    private String openId;
    private Object params;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "CommandBean{" +
                "cmd='" + cmd + '\'' +
                ", tag='" + tag + '\'' +
                ", token='" + token + '\'' +
                ", openId='" + openId + '\'' +
                ", params=" + params +
                '}';
    }
}
