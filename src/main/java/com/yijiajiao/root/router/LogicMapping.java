package com.yijiajiao.root.router;

import com.alibaba.fastjson.JSON;
import com.yijiajiao.root.bean.CommandBean;
import com.yijiajiao.root.utils.Config;
import com.yijiajiao.root.utils.HttpUtil;
import com.yijiajiao.root.utils.RootUtil;
import com.yijiajiao.root.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-03-09-15:17
 */
public class LogicMapping implements Runnable{

    private static final Logger log = LoggerFactory.getLogger(LogicMapping.class);
    private static final String SERVER = Config.getBaseString("server");
    private HttpServletRequest request;
    private HttpServletResponse response;
    private AsyncContext asyncContext;


    public LogicMapping(AsyncContext aCtx,HttpServletRequest request,HttpServletResponse response) {
        this.response = response;
        this.asyncContext = aCtx;
        this.request = request;
    }



    @Override
    public void run() {
        CommandBean command = getBody(this.request);
        log.info("__command:"+command);
        String uri = null;
        try {
            uri = Config.getBaseString(command.getCmd());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtil.isNotEmpty(uri)){
            Map<String,Object> head = new HashMap<>();
            head.put("tag",command.getTag());
            String res = HttpUtil.httpRest(SERVER, uri, head, command.getParams(), "POST");
            RootUtil.jsonResult(response,res);
        }
        asyncContext.complete();
    }

    private CommandBean getBody(HttpServletRequest request){
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()){
            sb = new StringBuilder();
            String line ;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSON.parseObject(sb.toString(),CommandBean.class);
    }

}
