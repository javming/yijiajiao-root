package com.yijiajiao.root.manage.servlet;

import com.yijiajiao.root.manage.model.RouterModel;
import com.yijiajiao.root.manage.model.ServerModel;
import com.yijiajiao.root.manage.service.RouterService;
import com.yijiajiao.root.manage.service.ServerService;
import com.yijiajiao.root.router.RouterTable;
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
 * @author zhaoming
 * @create 2017-04-06
 * @deprec 用于添加或者修改路由表根据type值判断  type=1添加；type=2修改；type=0删除
 */
public class UpdateRouterServlet extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(UpdateRouterServlet.class);
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = (String)req.getSession().getAttribute(Config.getBaseString("username"));
        if (StringUtil.isEmpty(token) || !token.equals(RedisUtil.getValue(Config.getBaseString("username")))){
            throw new RuntimeException("请登录操作！");
        }

        String type = req.getParameter("type");
        if ("0".equals(type)){
            log.info("删除==》");
            Integer requestId = Integer.parseInt(req.getParameter("requestId"));
            log.info("requestId == " + requestId);
            RouterService.remove( requestId );
        }else {

            String serverId = req.getParameter("serverId");
            if (serverId == null || serverId.equals("") ){
                throw new RuntimeException("请选择服务器");
            }
            ServerModel server = ServerService.detail(Integer.parseInt(serverId));
            String requestId = req.getParameter("requestId");
            String mappingUrl = Config.getString("ip_path") + server.getServerPort() + "/" + server.getServerName()
                    + req.getParameter("mappingUrl");
            RouterModel router = new RouterModel( requestId==null?null:Integer.parseInt(requestId),
                    req.getParameter("requestUrl"),
                    req.getParameter("requestMethod"),
                    req.getParameter("requestStatus"),
                    mappingUrl,
                    req.getParameter("routerStatus"),
                    req.getParameter("replaceRegex"),
                    req.getParameter("description"),
                    server.getServerId(),
                    null);

            if ("1".equals(type)){
                log.info("添加==》" + router);
                RouterService.addRouter(router);
            }

            if ("2".equals(type)){
                log.info("修改==》" + router);
                RouterService.updateRouter(router);
            }
        }

        RouterTable.routerInit();
        PrintWriter out = resp.getWriter();
        out.print("操作成功！");
        out.close();
    }

}
