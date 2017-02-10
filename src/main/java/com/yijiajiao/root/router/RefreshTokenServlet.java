package com.yijiajiao.root.router;

import com.yijiajiao.root.bean.ResultBean;
import com.yijiajiao.root.bean.SystemStatus;
import com.yijiajiao.root.utils.RootUtil;
import com.yijiajiao.root.utils.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于刷新token
 * 输出 json格式 {
 *                  "requestId": "2107826d52fe4e5587df5191c712c3db",
                    "code": 200,
                    "httpCode": "",
                    "message": "",
                    "result": {
                                "token":"TK754903C0ADDAD2CD",
                                "refreshToken":"bf965a7dac7528cb08249ba3e17bdf60"
                              }
                }
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2016-12-21-9:47
 */

@WebServlet(name = "RefreshTokenServlet",urlPatterns = "/refreshToken")
public class RefreshTokenServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(RefreshTokenServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        log.info("\n __[openId = "+request.getParameter("openId")+"]\n __[clientId = "+request.getParameter("clientId")
                +"]\n __[refreshToken = "+request.getParameter("refreshToken")+"]");
        String[] tokens = TokenUtil.refreshToken(request.getParameter("openId"), request.getParameter("clientId"),
                request.getParameter("refreshToken"));
        ResultBean result = new ResultBean();
        if (tokens == null ){
            result.setFailMsg(SystemStatus.TOKEN_TIME_OUT);
        }else {
            Map<String,String> map = new HashMap<String, String>();
            map.put("token",tokens[0]);
            map.put("refreshToken",tokens[1]);
            result.setSucResult(map);
        }
        RootUtil.jsonResult(response,result);
    }


}
