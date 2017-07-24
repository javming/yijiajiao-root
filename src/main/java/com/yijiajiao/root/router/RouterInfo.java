package com.yijiajiao.root.router;

public class RouterInfo{

    public final static String NEW_ROUTER       = "NEW_ROUTER";

    public final static String INSTALLED_ROUTER = "INSTALLED_ROUTER";

    public final static String LOGIN            = "LOGIN";

    public final static String UNLOGIN          = "UNLOGIN";

    /** 请求URL */
    private String             requestURL;

    /** 请求方法 */
    private String             requestMothed;

    /** 路由状态(是否为新加) */
    private String             routerStatus;

    /** 映射URL */
    private String             mappingURL;

    /** 请求状态（是否需要登录） */
    private String             requestStatus;

    /** 替换时使用的正则表达式 */
    private String             replaceRegex;

    public RouterInfo() {}

    public RouterInfo(String requestURL, String requestMothed, String routerStatus, String mappingURL, String requestStatus,
                      String replaceRegex) {
        this.requestURL = requestURL;
        this.requestMothed = requestMothed;
        this.routerStatus = routerStatus;
        this.mappingURL = mappingURL;
        this.requestStatus = requestStatus;
        this.replaceRegex = replaceRegex;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public String getRequestMothed() {
        return requestMothed;
    }

    public void setRequestMothed(String requestMothed) {
        this.requestMothed = requestMothed;
    }

    public String getRouterStatus() {
        return routerStatus;
    }

    public void setRouterStatus(String routerStatus) {
        this.routerStatus = routerStatus;
    }

    public String getMappingURL() {
        return mappingURL;
    }

    public void setMappingURL(String mappingURL) {
        this.mappingURL = mappingURL;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getReplaceRegex() {
        return replaceRegex;
    }

    public void setReplaceRegex(String replaceRegex) {
        this.replaceRegex = replaceRegex;
    }

    @Override
    public String toString() {
        return "RouterInfo{" +
                "requestURL='" + requestURL + '\'' +
                ", requestMothed='" + requestMothed + '\'' +
                ", routerStatus='" + routerStatus + '\'' +
                ", mappingURL='" + mappingURL + '\'' +
                ", requestStatus='" + requestStatus + '\'' +
                ", replaceRegex='" + replaceRegex + '\'' +
                '}';
    }
}