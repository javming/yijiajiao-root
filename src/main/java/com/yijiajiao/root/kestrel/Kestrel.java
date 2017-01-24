package com.yijiajiao.root.kestrel;

import com.yijiajiao.root.utils.Config;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.KestrelCommandFactory;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-01-18-15:03
 */
public class Kestrel {

    private final static String QUEUE_NAME = "KQ";

    private static final String server = Config.getString("kestrel.ip")+":"+ Config.getString("kestrel.port");

    private static MemcachedClient    memcachedClient;

    private static final Logger log        = LoggerFactory.getLogger(Kestrel.class);


    static  {
        MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(server));
        builder.setCommandFactory(new KestrelCommandFactory());
        try {
            memcachedClient = builder.build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发包
     */
    public static void send(Object value) {
        try {
            boolean flag = memcachedClient.set(QUEUE_NAME, 0, value);
            log.debug("send message : " + flag);
        } catch (Exception e) {
            log.info(e.toString(), e);
        }
    }


    /**
     * 收包
     */
    public String receive(MemcachedClient memcachedClient) {
        String message ="";
        try {
            message = memcachedClient.get(QUEUE_NAME);
            log.info("__[recieve message is:\n __"+message);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

}
