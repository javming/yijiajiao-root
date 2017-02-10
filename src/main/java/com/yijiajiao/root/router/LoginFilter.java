package com.yijiajiao.root.router;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yijiajiao.root.bean.ResultBean;
import com.yijiajiao.root.bean.SystemStatus;
import com.yijiajiao.root.utils.RootUtil;
import com.yijiajiao.root.utils.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebFilter(filterName = "loginFilter",value = "/*",asyncSupported = true)
public class LoginFilter implements Filter{
	private static final Logger log = LoggerFactory.getLogger(LoginFilter.class);

	public void init(FilterConfig filterConfig) throws ServletException {
		log.info(" LoginFilter init success !");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		log.info("\n __[请求地址：" + req.getPathInfo() + "]");
		RouterInfo routerInfo = RouterTable.getByRequestURL(req.getPathInfo(), req.getMethod());
		if ("/command".equals(req.getPathInfo()) || "/command/".equals(req.getPathInfo())) {
			BufferedReader reader = req.getReader();
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			JSONObject jsonObject = JSON.parseObject(sb.toString());
			if(!TokenUtil.verifyToken((String) jsonObject.get("token"), (String)jsonObject.get("openId"))){
				RootUtil.jsonResult(response, ResultBean.getFailResult(SystemStatus.TOKEN_TIME_OUT));
				reader.close();
				return;
			}
		} else if (routerInfo != null && RouterInfo.LOGIN.equals(routerInfo.getRequestStatus()) &&
				!TokenUtil.verifyToken(req.getHeader("token"), req.getHeader("openId"))) {

			ResultBean result = new ResultBean();
			result.setFailMsg(SystemStatus.TOKEN_TIME_OUT);
			RootUtil.jsonResult(response,result);
			return;
		}
		chain.doFilter(request,response);
	}

	public void destroy() {

	}

}
