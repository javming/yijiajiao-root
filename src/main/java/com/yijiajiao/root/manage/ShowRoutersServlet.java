package com.yijiajiao.root.manage;

import com.yijiajiao.root.router.RouterInfo;
import com.yijiajiao.root.router.RouterTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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
        RouterTable instance = RouterTable.getInstance();
        List<RouterInfo> routerInfos = instance.getRouterInfos();
        String condition = req.getParameter("condition");
        String keyWord = req.getParameter("keyWord");
        log.info("搜索条件：{ condition:" + condition + ";keyWord=" + keyWord + " }");
        if (condition!=null){
            List<RouterInfo> rout = new ArrayList<>();
            for (RouterInfo r : routerInfos){
                if ("requestURL".equals(condition)){
                    if (r.getRequestURL().contains(keyWord)){
                        rout.add(r);
                    }
                } else if ("requestMothed".equals(condition)){
                    if (r.getRequestMothed().equals(keyWord)){
                        rout.add(r);
                    }
                } else if ("mappingURL".equals(condition)){
                    if (r.getMappingURL().contains(keyWord)){
                        rout.add(r);
                    }
                } else if ("requestStatus".equals(condition)){
                    if (r.getRequestStatus().contains(keyWord)){
                        rout.add(r);
                    }
                }
            }
            req.setAttribute("condition",condition);
            req.setAttribute("keyWord",keyWord);
            routerInfos = rout;
        }
        req.setAttribute("routerInfos",routerInfos);
        req.getRequestDispatcher("/index.jsp").forward(req,resp);
    }
}
