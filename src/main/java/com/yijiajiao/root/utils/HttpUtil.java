package com.yijiajiao.root.utils;

import com.alibaba.fastjson.JSON;
import com.yijiajiao.root.bean.ResultBean;
import com.yijiajiao.root.bean.SystemStatus;
import com.yijiajiao.root.router.RouterInfo;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
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

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-01-23-10:25
 */
public class HttpUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
    private static CloseableHttpClient httpclient = null;
    private static IdleConnectionMonitorThread scanThread = null;

    /**
     * 初始化client对象.
     */
    static {
        // 连接池设置
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(20); // 最多20个连接
        cm.setDefaultMaxPerRoute(20); // 每个路由20个连接
        // 创建client对象
        httpclient = HttpClients.custom().setConnectionManager(cm).build();
        // 扫描无效连接的线程
        scanThread = new IdleConnectionMonitorThread(cm);
        scanThread.start();
        log.info("httpclient init success!");
    }
    /**
     * 关闭连接池.
     */
    public static void close() {
        if (httpclient != null) {
            try {
                httpclient.close();
            } catch (IOException e) {
            }
        }
        if (scanThread != null) {
            scanThread.shutdown();
        }
    }
    /**
     * Get方式取得URL的内容.
     *
     * @param url
     *            访问的网址
     * @return
     */
    public static String getUrlContent(String url) {
        // 参数检查
        if (httpclient == null) {
            throw new RuntimeException("httpclient not init.");
        }
        if (url == null || url.trim().length() == 0) {
            throw new RuntimeException("url is blank.");
        }
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(httpGet, HttpClientContext.create());
            // 转换结果
            HttpEntity entity1 = response.getEntity();
            String html = EntityUtils.toString(entity1);

            // 消费掉内容
            EntityUtils.consume(entity1);
            return html;
        } catch (IOException e) {
            return "";
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
            httpGet.releaseConnection();
        }
    }
    /**
     * Post方式取得URL的内容，默认为"application/x-www-form-urlencoded"格式，charset为UTF-8.
     *
     * @param url
     *            访问的网址
     * @param content
     *            提交的数据
     * @return
     */
    public static String postToUrl(String url, String content) {
        return postToUrl(url, content, "application/x-www-form-urlencoded",
                "UTF-8");
    }

    /**
     * json字符串形式请求
     *
     * @param url
     * @param content
     * @return
     */
    public static String postForJson(String url, String content) {
        return postToUrl(url, content, "application/json", "UTF-8");
    }

    /**
     * Post方式取得URL的内容.
     *
     * @param url
     *            访问的网址
     * @param content
     *            提交的数据
     * @return
     */
    public static String postToUrl(String url, String content,
                                   String contentType, String charset) {

        // 参数检查
        if (httpclient == null) {
            throw new RuntimeException("httpclient not init.");
        }
        if (url == null || url.trim().length() == 0) {
            throw new RuntimeException("url is blank.");
        }

        HttpPost httpPost = new HttpPost(url);

        // 设置内容
        ContentType type = ContentType.create(contentType,
                Charset.forName(charset));
        StringEntity reqEntity = new StringEntity(content, type);
        httpPost.setEntity(reqEntity);
        httpPost.addHeader("User-Agent",
                "Mozilla/4.0 (compatible; MSIE .0; Windows NT 6.1; Trident/4.0; SLCC2;)");
        httpPost.addHeader("Accept-Encoding", "*");

        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(30000).setConnectTimeout(30000).build();
        httpPost.setConfig(requestConfig);

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(httpPost, HttpClientContext.create());

            // 转换结果
            HttpEntity entity1 = response.getEntity();
            String html = EntityUtils.toString(entity1, charset);

            // 消费掉内容
            EntityUtils.consume(entity1);
            return html;
        } catch (IOException ex) {
            ex.printStackTrace();
            return "-1";
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
            httpPost.releaseConnection();
        }
    }


    public static String httpGet(RouterInfo routerInfo,HttpServletRequest request,String charset){
        URIBuilder builder = null;
        URI uri = null;
        try {
            builder = new URIBuilder(routerInfo.getMappingURL());
            for (java.util.Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
                String name = e.nextElement();
                builder.setParameter(name, request.getParameter(name));
            }
            uri = builder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setHeader("Accept-Charset", "utf-8;q=0.7,*;q=0.7");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            httpGet.addHeader(headerNames.nextElement(),request.getHeader(headerNames.nextElement()));
        }
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet,HttpClientContext.create());
            // 转换结果
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
            httpGet.releaseConnection();
        }
    }

    public static String httpPost(RouterInfo routerInfo, HttpServletRequest request, String charset) {
        HttpPost httpPost = new HttpPost(routerInfo.getMappingURL());
        BasicHttpEntity entity = new BasicHttpEntity();
        InputStream input = null;
        try {
            input = request.getInputStream();
        } catch (IOException e) {
            log.info(e.toString(), e);
        }
        entity.setContent(input);
        httpPost.setEntity(entity);
        httpPost.addHeader("Content-Type", request.getContentType());
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            httpPost.addHeader(headerNames.nextElement(),request.getHeader(headerNames.nextElement()));
        }
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost,HttpClientContext.create());
            HttpEntity entity1 = response.getEntity();
            return EntityUtils.toString(entity1,charset);
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

    public static String httpPut(RouterInfo routerInfo, HttpServletRequest request, String charset) {
        HttpPut httpPut = new HttpPut(routerInfo.getMappingURL());
        BasicHttpEntity entity = new BasicHttpEntity();
        InputStream input = null;
        try {
            input = request.getInputStream();
        } catch (IOException e) {
            log.info(e.toString(), e);
        }
        entity.setContent(input);
        httpPut.setEntity(entity);
        httpPut.addHeader("Content-Type", request.getContentType());
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            httpPut.addHeader(headerNames.nextElement(),request.getHeader(headerNames.nextElement()));
        }
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPut,HttpClientContext.create());
            HttpEntity entity1 = response.getEntity();
            return EntityUtils.toString(entity1,charset);
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
    public static String httpDelete(RouterInfo routerInfo, HttpServletRequest request, String charset) {
        URIBuilder builder = null;
        URI uri = null;
        try {
            builder = new URIBuilder(routerInfo.getMappingURL());
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
            httpDelete.addHeader(headerNames.nextElement(),request.getHeader(headerNames.nextElement()));
        }
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpDelete,HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity,charset);
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
