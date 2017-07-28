package com.yijiajiao.root.manage.servlet;

import com.alibaba.fastjson.JSON;
import com.yijiajiao.root.manage.model.RouterModel;
import com.yijiajiao.root.manage.service.RouterService;
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
import java.util.List;
import java.util.Map;

/**
 * @author zhaoming
 * @create 2017-04-06
 */
public class ShowRoutersServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(ShowRoutersServlet.class);
    @Override
    public void init() throws ServletException {
        System.out.println("servlet init success!");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String token = (String)req.getSession().getAttribute(Config.getBaseString("username"));
        if (StringUtil.isEmpty(token) || !token.equals(RedisUtil.getValue(Config.getBaseString("username")))){
            throw new RuntimeException("请登录操作！");
        }

        String condition = req.getParameter("condition");
        String keyWord = req.getParameter("keyWord");
        RouterModel param = new RouterModel();
        if (condition!=null){
            log.info("搜索条件：{ condition:" + condition + ";keyWord=" + keyWord + " }");
            if ("requestUrl".equals(condition)){
                param.setRequestUrl(keyWord);
            }
            else if ("requestMethod".equals(condition)){
                param.setRequestMethod(keyWord);
            }
            else if ("mappingUrl".equals(condition)){
                param.setMappingUrl(keyWord);
            }
            else if ("requestStatus".equals(condition)){
                param.setRequestStatus(keyWord);
            }
        }
        List<Map<String, Object>> list = RouterService.routersByConditions(param);
        log.info(JSON.toJSONString(list));
        //req.setAttribute("routers",list);
        //req.getRequestDispatcher("/index.jsp").forward(req,resp);
        PrintWriter out = resp.getWriter();
        out.print(JSON.toJSON(list));
        out.close();
    }
}
