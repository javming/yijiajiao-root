package com.yijiajiao.root.router;

import com.yijiajiao.root.utils.Config;
import com.yijiajiao.root.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.yijiajiao.root.utils.RootUtil.jsonResult;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-01-22-15:44
 */
public class HandleThread extends Thread {

    private static final Logger log = LoggerFactory.getLogger(HandleThread.class);
    private static final String CHARSET = Config.getString("charset");
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
                    res = HttpUtil.httpGet(routerInfo,request,CHARSET);
                    break;
                case "POST":
                    res = HttpUtil.httpPost(routerInfo,request,CHARSET);
                    break;
                case "PUT":
                    res = HttpUtil.httpPut(routerInfo,request,CHARSET);
                    break;
                case "DELETE":
                    res = HttpUtil.httpDelete(routerInfo,request,CHARSET);
                    break;
            }
            log.info("__其他系统返回：\n  "+res);
            jsonResult(response,res);

        }
        asyncContext.complete();
    }
}
