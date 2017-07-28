package com.yijiajiao.root.manage.servlet;

import com.yijiajiao.root.manage.model.ServerModel;
import com.yijiajiao.root.manage.service.ServerService;
import com.yijiajiao.root.utils.Config;
import com.yijiajiao.root.utils.RedisUtil;
import com.yijiajiao.root.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-07-27-18:03
 */
public class UpdateServerServlet extends HttpServlet{

    private static final Logger log = LoggerFactory.getLogger(UpdateServerServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = (String)req.getSession().getAttribute(Config.getBaseString("username"));
        if (StringUtil.isEmpty(token) || !token.equals(RedisUtil.getValue(Config.getBaseString("username")))){
            throw new RuntimeException("请登录操作！");
        }

        String type = req.getParameter("type");
        ServerModel server = new ServerModel(
                req.getParameter("serverId")==null?null:Integer.parseInt(req.getParameter("serverId")),
                req.getParameter("serverName"),
                req.getParameter("serverPort"),
                req.getParameter("serverAgent")
        );
        log.info("参数：" + server);
        if ( "1".equals(type)){
            ServerService.add(server);
        } else if ( "2".equals(type)){
            ServerService.update(server);
        }

        PrintWriter out = resp.getWriter();
        out.print("操作成功！");
        out.close();
    }
}
