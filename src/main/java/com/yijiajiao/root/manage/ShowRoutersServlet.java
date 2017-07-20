package com.yijiajiao.root.manage;

import com.yijiajiao.root.manage.model.RouterModel;
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
import java.util.List;

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
            req.setAttribute("msg","权限不足，操作取消！");
            req.getRequestDispatcher("/error.jsp").forward(req,resp);
            return;
        }

        String condition = req.getParameter("condition");
        String keyWord = req.getParameter("keyWord");
        RouterModel param = new RouterModel();
        if (condition!=null){
            log.info("搜索条件：{ condition:" + condition + ";keyWord=" + keyWord + " }");
            if ("requestURL".equals(condition)){
                param.setRequestUrl(keyWord);
            } else if ("requestMethod".equals(condition)){
                param.setRequestMethod(keyWord);
            } else if ("mappingURL".equals(condition)){
                param.setMappingUrl(keyWord);
            } else if ("requestStatus".equals(condition)){
                param.setRequestStatus(keyWord);
            }
            req.setAttribute("condition",condition);
            req.setAttribute("keyWord",keyWord);
        }
        List<RouterModel> list = RouterService.routersByConditions(param);

        req.setAttribute("routers",list);
        req.getRequestDispatcher("/index.jsp").forward(req,resp);
    }
}
