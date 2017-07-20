package com.yijiajiao.root.manage;

import com.yijiajiao.root.manage.model.RouterModel;
import com.yijiajiao.root.router.RouterTable;
import com.yijiajiao.root.utils.RedisUtil;
import com.yijiajiao.root.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhaoming
 * @create 2017-04-06
 * @deprec 用于添加或者修改路由表根据type值判断  type=1添加；type=2修改；type=0删除
 */
public class UpdateRouterServlet extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(UpdateRouterServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = (String)req.getSession().getAttribute("yijiajiao");
        if (StringUtil.isEmpty(token) || !token.equals(RedisUtil.getValue("yijiajiao"))){
            req.setAttribute("msg","权限不足，操作取消！");
            req.getRequestDispatcher("/error.jsp").forward(req,resp);
            return;
        }

        String type = req.getParameter("type");
        String requestURL = req.getParameter("requestURL");

        if ("1".equals(type)){
            RouterModel router = new RouterModel( null,
                                                requestURL,
                                                req.getParameter("requestMethod"),
                                                req.getParameter("requestStatus"),
                                                req.getParameter("mappingURL"),
                                                req.getParameter("routerStatus"),
                                                req.getParameter("replaceRegex"),
                                                "");
            RouterService.addRouter(router);
        }

        if ("2".equals(type)){
            RouterModel router = new RouterModel( Integer.parseInt(req.getParameter("requestId")),
                                                requestURL,
                                                req.getParameter("requestMethod"),
                                                req.getParameter("requestStatus"),
                                                req.getParameter("mappingURL"),
                                                req.getParameter("routerStatus"),
                                                req.getParameter("replaceRegex"),
                                                req.getParameter("description"));
            RouterService.updateRouter(router);
        }
        if ("0".equals(type)){
            Integer requestId = Integer.parseInt(req.getParameter("requestId"));
            RouterService.remove( requestId );
        }
        RouterTable.routerInit();
        resp.sendRedirect("/routers.action");

    }

}
