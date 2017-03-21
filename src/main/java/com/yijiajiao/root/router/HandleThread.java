package com.yijiajiao.root.router;

import com.yijiajiao.root.utils.HttpUtil;
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

import static com.yijiajiao.root.utils.RootUtil.jsonResult;

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
            log.info("__请求其他系统url:"+routerInfo.getMappingURL());
            String res = null;
            switch (request.getMethod()){
                case "GET":
                    res = HttpUtil.httpRest(routerInfo.getMappingURL()+"?",request.getQueryString(),getHeaders(),
                            null,"GET");
                    break;
                case "POST":
                    res = HttpUtil.httpRest(routerInfo.getMappingURL(),"",getHeaders(),getContent(),"POST");
                    break;
                case "PUT":
                    res = HttpUtil.httpRest(routerInfo.getMappingURL(),"",getHeaders(),getContent(),"PUT");
                    break;
                case "DELETE":
                    res = HttpUtil.httpRest(routerInfo.getMappingURL()+"?",request.getQueryString(),getHeaders(),
                            null,"DELETE");
                    break;
            }
            log.info("__其他系统返回：\n  "+res);
            jsonResult(response,res);
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
        headers.remove("Transfer-encoding");
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }
}
