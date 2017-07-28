package com.yijiajiao.root.manage.model;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-07-27-16:23
 */
public class ServerModel {

    private Integer serverId;
    private String serverName;
    private String serverPort;
    private String serverAgent; //服务器代理人

    public ServerModel() {}

    public ServerModel(Integer serverId, String serverName, String serverPort, String serverAgent) {
        this.serverId = serverId;
        this.serverName = serverName;
        this.serverPort = serverPort;
        this.serverAgent = serverAgent;
    }

    @Override
    public String toString() {
        return "ServerModel{" +
                "serverId=" + serverId +
                ", serverName='" + serverName + '\'' +
                ", serverPort='" + serverPort + '\'' +
                ", serverAgent='" + serverAgent + '\'' +
                '}';
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerAgent() {
        return serverAgent;
    }

    public void setServerAgent(String serverAgent) {
        this.serverAgent = serverAgent;
    }


}
