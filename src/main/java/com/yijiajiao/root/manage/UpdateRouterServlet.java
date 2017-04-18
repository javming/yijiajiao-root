package com.yijiajiao.root.manage;

import com.yijiajiao.root.router.RouterInfo;
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
import java.util.List;

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
        RouterTable instance = RouterTable.getInstance();
        List<RouterInfo> list = instance.getRouterInfos();
        String type = req.getParameter("type");
        String requestURL = req.getParameter("requestURL");
        if (!RouterTable.copy()){
            req.setAttribute("msg","文件备份出错，修改取消！");
            req.getRequestDispatcher("/index/detail.jsp");
            return;
        }
        if ("1".equals(type)){
            for (RouterInfo r: list){
                if (requestURL.equals(r.getRequestURL())){
                    log.error("该项目已存在，请勿重复添加");
                    req.setAttribute("msg","该项目已存在，请勿重复添加!");
                    req.getRequestDispatcher("/index/detail.jsp").forward(req,resp);
                    return;
                }
            }
            RouterInfo routerInfo = new RouterInfo(requestURL, req.getParameter("requestMothed"),
                    req.getParameter("routerStatus"), req.getParameter("mappingURL"),
                    req.getParameter("requestStatus"), req.getParameter("replaceRegex"));
            list.add(routerInfo);
            RouterTable.output();
            log.info("添加成功！");
        }
        if ("2".equals(type)){
            for (RouterInfo r: list){
                if (requestURL.equals(r.getRequestURL())){
                    r.setRouterStatus(req.getParameter("routerStatus"));
                    r.setReplaceRegex(req.getParameter("replaceRegex"));
                    r.setRequestStatus(req.getParameter("requestStatus"));
                    r.setRequestMothed(req.getParameter("requestMothed"));
                    r.setMappingURL(req.getParameter("mappingURL"));
                    RouterTable.output();
                    log.info("修改成功！");
                }
            }
        }
        if ("0".equals(type)){
            for (RouterInfo r: list){
                if (requestURL.equals(r.getRequestURL())){
                    list.remove(r);
                    RouterTable.output();
                    log.info("删除成功！");
                    break;
                }
            }
        }
        resp.sendRedirect("/routers.action");

    }

}
