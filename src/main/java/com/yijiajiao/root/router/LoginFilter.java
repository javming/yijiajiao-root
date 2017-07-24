package com.yijiajiao.root.router;

import com.alibaba.fastjson.JSON;
import com.yijiajiao.root.bean.CommandBean;
import com.yijiajiao.root.bean.ResultBean;
import com.yijiajiao.root.bean.SystemStatus;
import com.yijiajiao.root.utils.RootUtil;
import com.yijiajiao.root.utils.TokenUtil;
import com.yijiajiao.root.utils.XMemcachedUtil;
import net.rubyeye.xmemcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LoginFilter implements Filter{
	private static final Logger log = LoggerFactory.getLogger(LoginFilter.class);

	public void init(FilterConfig filterConfig) throws ServletException {
		log.info(" LoginFilter init success !");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		RouterInfo routerInfo = RouterTable.getByRequestURL(req.getRequestURI(), req.getMethod());
		if ("/command".equals(req.getRequestURI()) || "/command/".equals(req.getRequestURI())) {
			ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(req);
			String body = HttpHelper.getBodyString(requestWrapper);
			CommandBean command = JSON.parseObject(body, CommandBean.class);
			if(!TokenUtil.verifyToken(command.getToken(), command.getOpenId())){
				ResultBean result = new ResultBean();
				result.setFailMsg(SystemStatus.TOKEN_TIME_OUT);
				RootUtil.jsonResult(response,JSON.toJSONString(result));
				MemcachedClient memcachedClient = XMemcachedUtil.getMemcachedClient();
				log.info("memcacchedClien="+memcachedClient);
				try {
					log.info("set memcached  is :    " + command.getTag() + " = " + JSON.toJSONString(result));
					boolean flag = memcachedClient.set(command.getTag(), 7200, JSON.toJSONString(result));
					if (flag) log.info("set memcached success!");
				} catch (Exception e) {
					log.error("set MemcachedClient failed!!");
					e.printStackTrace();
				}
				return;
			}
			chain.doFilter(requestWrapper,response);
		} else if (routerInfo != null && RouterInfo.LOGIN.equals(routerInfo.getRequestStatus())
				&& !TokenUtil.verifyToken(req.getHeader("token"), req.getHeader("openId"))) {
			log.error("登录信息已过期或登录信息为空！！！");
			RootUtil.jsonResult(response, JSON.toJSONString(ResultBean.getFailResult(SystemStatus.TOKEN_TIME_OUT)));
			return;
		}else {
			chain.doFilter(request,response);
		}

	}

	public void destroy() {

	}

}
