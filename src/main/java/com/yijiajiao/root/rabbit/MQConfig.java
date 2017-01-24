package com.yijiajiao.root.rabbit;

import com.yijiajiao.root.utils.Config;

public class MQConfig {

    protected final static String USERNAME= Config.getString("rabbit.username");

    protected final static String PASSWORD = Config.getString("rabbit.password");

    protected final static String HOST = Config.getString("rabbit.hosts");

    protected final static int PORT = Integer.parseInt(Config.getString("rabbit.port"));

    protected final static String QUEUENAME=Config.getString("rabbit.queuename");

    protected final static String ROUTINGKEY =Config.getString("rabbit.routingkey");

    protected final static String EXCHANGENAME=Config.getString("rabbit.exchangename");

    protected final static int CONNECTION_MAX_COUNT=Integer.parseInt(Config.getString("rabbit.connectionmaxcount"));

    protected final static int CONNECTION_DEFAULT_COUNT=Integer.parseInt(Config.getString("rabbit.connectiondefaultcount"));

    protected final static long FREE_TIME = Long.parseLong(Config.getString("rabbit.connectionfreetime"));

}
