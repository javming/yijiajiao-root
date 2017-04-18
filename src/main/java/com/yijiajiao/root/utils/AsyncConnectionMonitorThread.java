package com.yijiajiao.root.utils;

import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;

import java.util.concurrent.TimeUnit;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-04-01-14:57
 */
public class AsyncConnectionMonitorThread extends Thread{
    private final PoolingNHttpClientConnectionManager cm;
    private volatile boolean shutdown;

    public AsyncConnectionMonitorThread(PoolingNHttpClientConnectionManager connMgr) {
        super();
        this.cm = connMgr;
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(5000);
                    // 关闭无效连接
                    cm.closeExpiredConnections();
                    // 可选, 关闭空闲超过30秒的
                    cm.closeIdleConnections(30, TimeUnit.SECONDS);
                }
            }
        } catch (InterruptedException ex) {
            // terminate
        }
    }

    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }
}
