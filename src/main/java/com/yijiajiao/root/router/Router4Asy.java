package com.yijiajiao.root.router;

import com.yijiajiao.root.kestrel.Kestrel;
import com.yijiajiao.root.rabbit.SendMessage;
import com.yijiajiao.root.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-01-18-13:37
 */

@WebServlet(name = "Router4Asy", urlPatterns = { "/*" }, asyncSupported = true)
public class Router4Asy extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(Router4Asy.class);

    public void init() throws ServletException {
        try {
            RouterTable.getInstance();
            log.info("init 实例化可配置路由文件");
        } catch (IOException e) {
            log.info(e.toString(), e);
        }
        new Thread(new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        int i = Config.getInt("system.router.delay");
                        Thread.sleep(i * 1000);
                    } catch (InterruptedException e) {
                        log.info(e.toString(), e);
                    }
                    RouterTable.reload();
                }
            }
        }).start();
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info(" __[request_path:" + request.getPathInfo()+"]\n __[query_param:"+request.getQueryString()
                +"]\n __[request_method:"+request.getMethod()+"]");
        if ("/command".equals(request.getPathInfo())||"/command/".equals(request.getPathInfo())) {
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            String data = sb.toString();
            log.info("send message_data:\n "+data);
            int asyncType=Config.getInt("asynctype");
            //选择消息服务器 1 表示kestrel， 2 表示rabbitmq
            if (1 == asyncType){
                log.info("__发送消息到kestrel");
                Kestrel.send(data);
            }else if (asyncType==2){
                log.info("__发送消息到rabbitmq");
                SendMessage.sendMessage(data);
            }
            log.info("发送成功！");
        } else {
            AsyncContext aCtx = request.startAsync(request, response);
            aCtx.start(new HandleThread(aCtx, request, response));
        }
    }
}
