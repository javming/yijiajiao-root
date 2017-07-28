package com.yijiajiao.root.manage.model;

import java.util.Date;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-07-10-10:55
 */
public class RouterModel {

    private Integer requestId;
    private String requestUrl; //路由路径
    private String requestMethod; //请求方法',
    private String requestStatus; //登录权限限制
    private String mappingUrl; //二级路径
    private String routerStatus;
    private String replaceRegex;
    private String description; //描述
    private Integer serverId;
    private Date updateTime;

    public RouterModel() {
    }

    public RouterModel(Integer requestId, String requestUrl, String requestMethod, String requestStatus,
                       String mappingUrl, String routerStatus, String replaceRegex, String description,
                       Integer serverId, Date updateTime) {
        this.requestId = requestId;
        this.requestUrl = requestUrl;
        this.requestMethod = requestMethod;
        this.requestStatus = requestStatus;
        this.mappingUrl = mappingUrl;
        this.routerStatus = routerStatus;
        this.replaceRegex = replaceRegex;
        this.description = description;
        this.serverId = serverId;
        this.updateTime = updateTime;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getMappingUrl() {
        return mappingUrl;
    }

    public void setMappingUrl(String mappingUrl) {
        this.mappingUrl = mappingUrl;
    }

    public String getRouterStatus() {
        return routerStatus;
    }

    public void setRouterStatus(String routerStatus) {
        this.routerStatus = routerStatus;
    }

    public String getReplaceRegex() {
        return replaceRegex;
    }

    public void setReplaceRegex(String replaceRegex) {
        this.replaceRegex = replaceRegex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "RouterModel{" +
                "requestId=" + requestId +
                ", requestUrl='" + requestUrl + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", requestStatus='" + requestStatus + '\'' +
                ", mappingUrl='" + mappingUrl + '\'' +
                ", routerStatus='" + routerStatus + '\'' +
                ", replaceRegex='" + replaceRegex + '\'' +
                ", description='" + description + '\'' +
                ", serverId=" + serverId +
                ", updateTime=" + updateTime +
                '}';
    }
}
