package com.yijiajiao.root.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-01-22-9:56
 */
public class SendMessage extends MQConfig{
    private static Logger log = LoggerFactory.getLogger(SendMessage.class);
    private static ConnectionFactory factory = new ConnectionFactory();
    private static Connection connection = null;
    private static Date freeTime=new Date();

    static {
        factory.setHost(HOST);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        log.info("初始化Connectionfactory完成！");
        //定时查看connection空闲时间，超时关闭
        new CheckConnectionThread().start();
    }

    private static void init(){
        if(connection==null){
            try {
                connection = factory.newConnection();
            } catch (Exception e) {
                log.info("connection创建失败！");
                e.printStackTrace();
            }
            freeTime=new Date();
            log.info("rabbitmq-connection初始化完成!");
        }
    }

    public  static void sendMessage(String message){
        Channel channel=null;
        init();
        try {
            channel = createChannel();
            channel.basicPublish(EXCHANGENAME, ROUTINGKEY, null, HessionCodecFactory.serialize(message));
        } catch (Exception e){
            e.printStackTrace();
        }finally{
            try {
                channel.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static Channel createChannel() throws IOException {
        Channel channel = connection.createChannel();
        //exchangeName：交换机名字 exchangeType(direct)类型 durable是否持久化 autoDelete不使用时是否自动删除
        channel.exchangeDeclare(EXCHANGENAME,"direct",true);
        String queueName = channel.queueDeclare(QUEUENAME,true,false,false,null).getQueue();
        channel.queueBind(queueName, EXCHANGENAME, ROUTINGKEY);
        freeTime=new Date();
        return channel;
    }

    static class CheckConnectionThread extends Thread{
        @Override
        public void run(){
            log.info("校验connection闲置时间线程启动...");
            while(true){
                long time = new Date().getTime()-freeTime.getTime();
                if ((time)>FREE_TIME && connection!=null){
                    log.info("connection 已闲置"+time/1000+"s ,超过规定闲时时长"+FREE_TIME/1000+"s,关闭！");
                    try {
                        connection.close();
                        connection=null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(FREE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
