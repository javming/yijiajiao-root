package com.yijiajiao.root.utils;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-04-13-14:42
 */
public class XMemcachedUtil {

    private static final Logger log = LoggerFactory.getLogger(XMemcachedUtil.class);
    private static MemcachedClientBuilder builder = null;
    private static MemcachedClient memcachedClient = null;

    static {
        builder = new XMemcachedClientBuilder(AddrUtil.getAddressMap(Config.getString("memcached_server")));
    }

    /**
     * 获取MemcachedClient
     *
     * @return
     */
    public static MemcachedClient getMemcachedClient(){
        try {
            if(memcachedClient == null){
                memcachedClient = builder.build();
                //当选择用URL当key的时候，MemcachedClient会自动将URL encode再存储。默认是关闭的
                memcachedClient.setSanitizeKeys(true);
                //memcached存储大数据的效率是比较低的，当数据比较大的时候xmemcached会帮你压缩在存储，取出来的时候自动解压并反序列化，这个大小阀值默认是16K
                memcachedClient.getTranscoder().setCompressionThreshold(32768);
                return memcachedClient;
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("获取MemcachedClient失败!");
        }
        return memcachedClient;
    }

    /**
     * 关闭MemcachedClient
     *
     * @param memcachedClient
     */
    public static void close(MemcachedClient memcachedClient){
        if(memcachedClient != null && !memcachedClient.isShutdown()){
            try {
                memcachedClient.shutdown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setMemcached(String key, int seconds, String value){
        MemcachedClient memcachedClient = getMemcachedClient();
        if (memcachedClient != null){
            try {
                boolean set = memcachedClient.set(key, seconds, value);
                if (set) log.info("set memcached success >> " + key + ":" + value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            log.error("set memcached failed!");
        }
    }
}
