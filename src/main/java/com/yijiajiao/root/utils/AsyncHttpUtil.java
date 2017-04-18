package com.yijiajiao.root.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 异步httpclient
 *
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-04-01-14:54
 */
public class AsyncHttpUtil {
    private final static int MAX_TOTAL_CONNECTIONS = Config.getInt("http.maxtotal");
    private final static int MAX_ROUTE_CONNECTIONS = Config.getInt("http.defaultmaxperroute");
    private final static String APPLICATION_JSON = "application/json";
    private static final String CHARSET="UTF-8";
    private static final Logger log = LoggerFactory.getLogger(AsyncHttpUtil.class);
    private static CloseableHttpAsyncClient httpAsyncClient = null;
    private static AsyncConnectionMonitorThread scanThread = null;
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final long DEFEAT_TIMEOUT = 10; // 默认10秒超时
    private static final TimeUnit DEFEAT_UNIT = TimeUnit.SECONDS; // 秒

    static {
        init();
    }

    /**
     * 初始化client对象.
     */
    private static void init(){
        // 连接池设置
        ConnectingIOReactor ioReactor = null;
        try {
            ioReactor = new DefaultConnectingIOReactor();
            PoolingNHttpClientConnectionManager cm = new PoolingNHttpClientConnectionManager(ioReactor);
            cm.setMaxTotal(MAX_TOTAL_CONNECTIONS); // 最大连接数
            cm.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS); // 每个路由的最大连接数
            httpAsyncClient = HttpAsyncClients.custom().setConnectionManager(cm).build();
            httpAsyncClient.start();
            // 扫描无效连接的线程
            scanThread = new AsyncConnectionMonitorThread(cm);
            scanThread.start();
            log.info("async-httpclient-pool init success!");
        } catch (IOReactorException e) {
            e.printStackTrace();
            log.error("async-httpclient-pool init failed!");
        }

    }
    /**
     * 关闭连接池.
     */
    public static void close() {
        if (httpAsyncClient != null) {
            try {
                httpAsyncClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (scanThread != null) {
            scanThread.shutdown();
        }
    }

    /** content-type:application/json */
    public static String httpRest(String server, String uri, Map<String, Object> headers, Object body, String method){
        return httpRest(server,uri,headers,body,method,APPLICATION_JSON);
    }

    public static String httpRest(String server,String uri,Map<String, Object> headers,Object body,String method,
                                  String contentType){
        String url = server+uri;
        // 参数检查
        if (httpAsyncClient == null) {
            init();
        }
        log.info(" 请求其它系统路径：===>>\n __["+url+"]"+
                (headers==null?"":("\n __[headParams]:"+JSON.toJSONString(headers)))+
                (body==null?"": ("\n __[bodyParams]:"+ JSON.toJSONString(body))));
        String res ;
        switch (method){
            case "GET":
                res = httpGet(url,headers);break;
            case "POST":
                res = httpPost(url,headers,body,contentType);break;
            case "PUT":
                res = httpPut(url,headers,body,contentType);break;
            case "DELETE":
                res = httpDelete(url,headers);break;
            default:
                log.error("The request-method is not defined");
                throw new RuntimeException("The request-method is not defined");
        }
        return res;
    }

    /**
     * 添加头信息
     */
    private static HttpUriRequest setHeaders(HttpUriRequest request, Map<String, Object> headers){
        Iterator i = headers.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry en = (Map.Entry) i.next();
            request.setHeader((String) en.getKey(), (String) en.getValue());
        }
        return request;
    }

    public static String getResult(Future<HttpResponse> future, Long timeOut, String charset) {
        HttpResponse response;
        String result;

        Long s_timeOut = timeOut == null ? DEFEAT_TIMEOUT : timeOut;
        String s_chareset = charset == null ? DEFAULT_CHARSET : charset;
        try {
            response = future.get(s_timeOut, DEFEAT_UNIT);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity,s_chareset);
            log.info("其他系统返回：<<===\n __" + result);
        } catch (Exception e) {
            throw new RuntimeException("获取结果出现错误：" + e.getMessage());
        }
        return result;
    }

    private static String execute(final HttpUriRequest request){
        final CountDownLatch latch = new CountDownLatch(1);
        Future<HttpResponse> execute = httpAsyncClient.execute(request, new FutureCallback<HttpResponse>() {
            public void completed(final HttpResponse response) {
                latch.countDown();
                log.info(request.getRequestLine() + "->" + response.getStatusLine());
            }
            public void failed(final Exception ex) {
                latch.countDown();
                log.error(request.getRequestLine() + "->" + ex);
            }

            public void cancelled() {
                latch.countDown();
                log.error(request.getRequestLine() + " cancelled");
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return getResult(execute,null,null);
    }

    public static String httpGet(String url,Map<String, Object> headers){
        HttpGet httpGet = new HttpGet(url);
        if(headers!=null&&!headers.isEmpty()){
            httpGet = (HttpGet) setHeaders(httpGet,headers);
        }
        String res = execute(httpGet);
        httpGet.releaseConnection();
        return res;
    }

    public static String httpPost(String url,Map<String, Object> headers,Object body,String contentType){
        String entity = null;
        if (body != null) entity = JSON.toJSONString(body);
        return httpPost(url,headers,entity,contentType);
    }
    public static String httpPost(String url,Map<String, Object> headers,String entity,String contentType){
        HttpPost httpPost = new HttpPost(url);
        if(headers!=null&&!headers.isEmpty()){
            httpPost = (HttpPost) setHeaders(httpPost,headers);
        }
        // 设置内容
        if (entity!=null){
            ContentType type = ContentType.create(contentType, Charset.forName(CHARSET));
            StringEntity stringEntity = new StringEntity(entity,type);
            httpPost.setEntity(stringEntity);
        }
        String res = execute(httpPost);
        httpPost.releaseConnection();
        return res;
    }

    public static String httpPut(String url,Map<String, Object> headers,Object body,String contentType){
        HttpPut httpPut = new HttpPut(url);
        if(headers!=null&&!headers.isEmpty()){
            httpPut = (HttpPut) setHeaders(httpPut,headers);
        }
        // 设置内容
        if (body!=null){
            ContentType type = ContentType.create(contentType,Charset.forName(CHARSET));
            StringEntity entity = new StringEntity(JSON.toJSONString(body),type);
            httpPut.setEntity(entity);
        }
        String res = execute(httpPut);
        httpPut.releaseConnection();
        return res;
    }

    public static String httpDelete(String url,Map<String, Object> headers){
        HttpDelete httpDelete = new HttpDelete(url);
        if(headers!=null&&!headers.isEmpty()){
            httpDelete = (HttpDelete) setHeaders(httpDelete,headers);
        }
        String res = execute(httpDelete);
        httpDelete.releaseConnection();
        return res;
    }
}

