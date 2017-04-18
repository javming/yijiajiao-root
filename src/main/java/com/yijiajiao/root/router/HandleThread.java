package com.yijiajiao.root.router;

import com.alibaba.fastjson.JSON;
import com.yijiajiao.root.utils.HttpUtil;
import com.yijiajiao.root.utils.RootUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-01-22-15:44
 */
public class HandleThread extends Thread {

    private static final Logger log = LoggerFactory.getLogger(HandleThread.class);
    private HttpServletRequest  request;
    private HttpServletResponse response;
    private AsyncContext        asyncContext;
    private RouterInfo          routerInfo = null;

    public HandleThread(AsyncContext aCtx, HttpServletRequest request, HttpServletResponse response) {
        this.asyncContext = aCtx;
        this.request = request;
        this.response = response;
        this.asyncContext.setTimeout(300000);
    }

    @Override
    public void run(){
        routerInfo = RouterTable.getByRequestURL(request.getPathInfo(), request.getMethod());
        if (null!= routerInfo){
            String url = routerInfo.getMappingURL();
            log.info("__请求其他系统url:"+url);
            String res = null;
            switch (request.getMethod()){
                case "GET":
                    res = HttpUtil.httpRest(url+"?",request.getQueryString(),getHeaders(), null,"GET");
                    break;
                case "POST":
                    res = HttpUtil.httpRest(url,"",getHeaders(),getContent(),"POST");
                    break;
                case "PUT":
                    res = HttpUtil.httpRest(url,"",getHeaders(),getContent(),"PUT");
                    break;
                case "DELETE":
                    res = HttpUtil.httpRest(url+"?",request.getQueryString(),getHeaders(),null,"DELETE");
                    break;
            }
            RootUtil.jsonResult(response,res);
        }
        asyncContext.complete();
    }

    private Map<String,Object> getHeaders(){
        Map<String,Object> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String next = headerNames.nextElement();
            headers.put(next,request.getHeader(next));
        }
        headers.remove("Content-Length");
        headers.remove("Transfer-Encoding");
        headers.remove("Host");
        return headers;
    }

    private Object getContent(){
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            sb = new StringBuilder();
            String line ;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSON.parseObject(sb.toString());
    }
}
