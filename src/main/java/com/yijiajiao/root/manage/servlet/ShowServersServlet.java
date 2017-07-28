package com.yijiajiao.root.manage.servlet;

import com.alibaba.fastjson.JSON;
import com.yijiajiao.root.manage.service.ServerService;
import com.yijiajiao.root.utils.Config;
import com.yijiajiao.root.utils.RedisUtil;
import com.yijiajiao.root.utils.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-07-27-17:27
 */
public class ShowServersServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = (String)req.getSession().getAttribute(Config.getBaseString("username"));
        if (StringUtil.isEmpty(token) || !token.equals(RedisUtil.getValue(Config.getBaseString("username")))){
            throw new RuntimeException("请登录操作！");
        }

        String serverId = req.getParameter("serverId");
        Object result;
        if (serverId != null && !"".equals(serverId)){
            int id = Integer.parseInt(serverId);
            result = ServerService.detail(id);
        } else {
            result = ServerService.all();
        }

        System.out.println("结果：" + JSON.toJSONString(result));

        PrintWriter out = resp.getWriter();
        out.print(JSON.toJSON(result));
        out.close();
    }

}
