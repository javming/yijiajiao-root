package com.yijiajiao.root.command.service;

import com.yijiajiao.rabbitmq.util.Config;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-01-11-11:22
 */
public class BasicLogic {

    protected static final String wares_server = Config.getString("wares_server");
    protected static final String user_server = Config.getString("user_server");
    protected static final String teach_server = Config.getString("teach_server");
    protected static final String sale_server = Config.getString("sale_server");
    protected static final String solution_server = Config.getString("solution_server");
    protected static final String finance_server = Config.getString("finance_server");
    protected static final String msg_server = Config.getString("msg_server");
    protected static final String oss_server = Config.getString("oss_server");
    protected static final String promotion_server = Config.getString("promotion_server");

}
