package com.yijiajiao.root.utils;

import com.alibaba.fastjson.JSON;
import com.yijiajiao.root.bean.ResultBean;
import com.yijiajiao.root.bean.SystemStatus;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;


/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-01-23-10:25
 */
public class HttpUtil {

    private final static int MAX_TOTAL_CONNECTIONS = Config.getInt("http.maxtotal");
    private final static int MAX_ROUTE_CONNECTIONS = Config.getInt("http.defaultmaxperroute");
    private final static String APPLICATION_JSON = "application/json";
    private static final String CHARSET="UTF-8";
    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
    private static CloseableHttpClient httpclient = null;
    private static IdleConnectionMonitorThread scanThread = null;

    /**
     * 初始化client对象.
     */
    static {
        // 连接池设置
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(MAX_TOTAL_CONNECTIONS); // 最大连接数
        cm.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS); // 每个路由的最大连接数
        // 创建client对象
        httpclient = HttpClients.custom().setConnectionManager(cm).build();
        // 扫描无效连接的线程
        scanThread = new IdleConnectionMonitorThread(cm);
        scanThread.start();
        log.info("httpclient-pool init success!");
    }
    /**
     * 关闭连接池.
     */
    public static void close() {
        if (httpclient != null) {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (scanThread != null) {
            scanThread.shutdown();
        }
    }

    /** content-type:application/json */
    public static String httpRest(String server,String uri,Map<String, Object> headers,Object body,String method){
        return httpRest(server,uri,headers,body,method,APPLICATION_JSON);
    }

    public static String httpRest(String server,String uri,Map<String, Object> headers,Object body,String method,
                                  String contentType){
        String url = "http://"+server+uri;
        // 参数检查
        if (httpclient == null) {
            throw new RuntimeException("httpclient not init.");
        }
        if (StringUtil.isEmpty(url)) {
            throw new RuntimeException("url is blank.");
        }
        log.info(" 请求其它系统路径：===>>\n __[http://"+url+"]"+
                (headers==null?"":("\n __[headParams]:"+JSON.toJSONString(headers)))+
                (body==null?"": ("\n __[bodyParams]:"+ JSON.toJSONString(body))));
        String res = null;
        switch (method){
            case "GET":
                res = httpGet(url,headers,CHARSET);break;
            case "POST":
                res = httpPost(url,headers,body,contentType);break;
            case "PUT":
                res = httpPut(url,headers,body,contentType,CHARSET);break;
            case "DELETE":
                res = httpDelete(url,headers,CHARSET);break;
            default:
                log.error("The request-method is not defined");
                throw new RuntimeException("The request-method is not defined");
        }
        log.info("其他系统返回：<<===\n __"+res);
        return res;
    }

    /**
     * 添加头信息
     */
    private static HttpUriRequest setHeaders(HttpUriRequest request,Map<String, Object> headers){
        Iterator i = headers.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry en = (Map.Entry) i.next();
            request.addHeader((String) en.getKey(), (String) en.getValue());
        }
        return request;
    }

    private static String execute(HttpUriRequest request,String charset){
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(request,HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, charset);
        } catch (IOException e) {
            e.printStackTrace();
            return JSON.toJSONString(ResultBean.getFailResult(SystemStatus.SERVER_ERROR));
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error("e:"+e.getMessage());
                }
            }
        }
    }

    public static String httpGet(String url,Map<String, Object> headers,String charset){
        HttpGet httpGet = new HttpGet(url);
        if(headers!=null&&!headers.isEmpty()){
            httpGet = (HttpGet) setHeaders(httpGet,headers);
        }
        String res = execute(httpGet,charset);
        httpGet.releaseConnection();
        return res;
    }

    public static String httpPost(String url,Map<String, Object> headers,Object body,String contentType){
        String entity = null;
        if (body != null) entity = JSON.toJSONString(body);
        return httpPost(url,headers,entity,contentType,CHARSET);
    }
    public static String httpPost(String url,Map<String, Object> headers,String entity,String contentType,
                                 String charset){
        HttpPost httpPost = new HttpPost(url);
        if(headers!=null&&!headers.isEmpty()){
            httpPost = (HttpPost) setHeaders(httpPost,headers);
        }
        // 设置内容
        if (entity!=null){
            ContentType type = ContentType.create(contentType,Charset.forName(charset));
            StringEntity stringEntity = new StringEntity(entity,type);
            httpPost.setEntity(stringEntity);
        }
        String res = execute(httpPost, charset);
        httpPost.releaseConnection();
        return res;
    }

    public static String httpPut(String url,Map<String, Object> headers,Object body,String contentType,
                                 String charset){
        HttpPut httpPut = new HttpPut(url);
        if(headers!=null&&!headers.isEmpty()){
            httpPut = (HttpPut) setHeaders(httpPut,headers);
        }
        // 设置内容
        if (body!=null){
            ContentType type = ContentType.create(contentType,Charset.forName(charset));
            StringEntity entity = new StringEntity(JSON.toJSONString(body),type);
            httpPut.setEntity(entity);
        }
        String res = execute(httpPut, charset);
        httpPut.releaseConnection();
        return res;
    }

    public static String httpDelete(String url,Map<String, Object> headers,String charset){
        HttpDelete httpDelete = new HttpDelete(url);
        if(headers!=null&&!headers.isEmpty()){
            httpDelete = (HttpDelete) setHeaders(httpDelete,headers);
        }
        String res = execute(httpDelete,charset);
        httpDelete.releaseConnection();
        return res;
    }


    public static String httpGet(String url,HttpServletRequest request){
        URIBuilder builder = null;
        URI uri = null;
        try {
            builder = new URIBuilder(url);
            for (java.util.Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
                String name = e.nextElement();
                builder.setParameter(name, request.getParameter(name));
            }
            uri = builder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpGet httpGet = new HttpGet(uri);
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String next = headerNames.nextElement();
            httpGet.setHeader(next,request.getHeader(next));
        }
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet,HttpClientContext.create());
            // 转换结果
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, CHARSET);
        } catch (IOException e) {
            e.printStackTrace();
            return JSON.toJSONString(ResultBean.getFailResult(SystemStatus.SERVER_ERROR));
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error("e:"+e.getMessage());
                }
            }
            httpGet.releaseConnection();
        }
    }

    public static String httpPost(String url, HttpServletRequest request) {
        HttpPost httpPost = new HttpPost(url);
        BasicHttpEntity entity = new BasicHttpEntity();
        InputStream input = null;
        try {
            input = request.getInputStream();
        } catch (IOException e) {
            log.info(e.toString(), e);
        }
        entity.setContent(input);
        httpPost.setEntity(entity);
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String next = headerNames.nextElement();
            httpPost.setHeader(next,request.getHeader(next));
        }
        httpPost.removeHeaders("Content-Length");
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost,HttpClientContext.create());
            HttpEntity entity1 = response.getEntity();
            return EntityUtils.toString(entity1,CHARSET);
        } catch (IOException e) {
            e.printStackTrace();
            return JSON.toJSONString(ResultBean.getFailResult(SystemStatus.SERVER_ERROR));
        }finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            httpPost.releaseConnection();
        }
    }

    public static String httpPut(String url, HttpServletRequest request) {
        HttpPut httpPut = new HttpPut(url);
        BasicHttpEntity entity = new BasicHttpEntity();
        InputStream input = null;
        try {
            input = request.getInputStream();
        } catch (IOException e) {
            log.info(e.toString(), e);
        }
        entity.setContent(input);
        httpPut.setEntity(entity);
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String next = headerNames.nextElement();
            httpPut.setHeader(next,request.getHeader(next));
        }
        httpPut.removeHeaders("Content-Length");
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPut,HttpClientContext.create());
            HttpEntity entity1 = response.getEntity();
            return EntityUtils.toString(entity1,CHARSET);
        } catch (IOException e) {
            e.printStackTrace();
            return JSON.toJSONString(ResultBean.getFailResult(SystemStatus.SERVER_ERROR));
        }finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error(e.toString(),e.getMessage());
                }
            }
            httpPut.releaseConnection();
        }
    }
    public static String httpDelete(String url, HttpServletRequest request) {
        URIBuilder builder = null;
        URI uri = null;
        try {
            builder = new URIBuilder(url);
            for (java.util.Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
                String name = e.nextElement();
                builder.setParameter(name, request.getParameter(name));
            }
            uri = builder.build();
        } catch (URISyntaxException e) {
            log.info(e.toString(), e);
        }
        HttpDelete httpDelete = new HttpDelete(uri);
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String next = headerNames.nextElement();
            httpDelete.setHeader(next,request.getHeader(next));
        }
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpDelete,HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity,CHARSET);
        } catch (IOException e) {
            e.printStackTrace();
            return JSON.toJSONString(ResultBean.getFailResult(SystemStatus.SERVER_ERROR));
        }finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error(e.toString(),e);
                }
            }
            httpDelete.releaseConnection();
        }
    }

}
