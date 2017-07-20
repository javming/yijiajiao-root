package com.yijiajiao.root.router;


import com.yijiajiao.root.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-01-18-14:02
 */
public class CharacterEncodingFilter implements Filter {

    private static Logger log = LoggerFactory.getLogger(CharacterEncodingFilter.class);
    private String encoding ;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encode");
        if (StringUtil.isEmpty(encoding)){
            encoding = "UTF-8";
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long _start = System.currentTimeMillis();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        log.info("[pathInfo:"+request.getPathInfo()+"]");
        log.info("[requestURL:"+request.getRequestURL()+"]");
        log.info("[requestURI:"+request.getRequestURI()+"]");
        log.info("[contextPath:"+request.getContextPath()+"]");
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        //response.setContentType("application/json; charset=utf-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        response.addHeader("Access-Control-Max-Age", "1800");//30 min
        filterChain.doFilter(request,response);
        long _end = System.currentTimeMillis();
        log.info("【本次请求耗时："+(_end-_start)+"ss】");
    }

    @Override
    public void destroy() {

    }
}
