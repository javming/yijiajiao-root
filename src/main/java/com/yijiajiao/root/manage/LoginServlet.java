package com.yijiajiao.root.manage;

import com.yijiajiao.root.utils.Config;
import com.yijiajiao.root.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-04-18-11:11
 */
public class LoginServlet extends HttpServlet{
    private final Logger log = LoggerFactory.getLogger(LoginServlet.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        log.info("username:" + username);
        log.info("password:" + password);
        String baseUsername = Config.getBaseString("username");
        String basePassword = Config.getBaseString("password");
        if (!username.equals(baseUsername) || !password.equals(basePassword)){
            req.setAttribute("msg","用户名或密码错误！");
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
            return;
        }
        String token = UUID.randomUUID().toString().trim().replaceAll("-", "");
        RedisUtil.putRedis(username,token,7200);
        req.getSession().setAttribute(username,token);
        resp.sendRedirect("/routers.action");
    }
}
