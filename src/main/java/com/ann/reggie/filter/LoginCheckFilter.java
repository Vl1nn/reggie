package com.ann.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.ann.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录过滤器
 */
@Component
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //实例化路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER  = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
//        1.获取请求url
        String uri = request.getRequestURI();
        log.info("请求地址：{}",uri);
        //定义不需要处理的的uri合集
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
        };
//        2.判断本次请求是否需要拦截 参数：放行地址合集，本次请求地址
        boolean check = check(urls, uri);
        if (check){
            log.info("本次请求：{}不需要处理",uri);
            filterChain.doFilter(request,response);
            return;
        }
//        判断用户是否登录
        if (request.getSession().getAttribute("employee") != null) {
            log.info("user logged in");
            filterChain.doFilter(request,response);
            return;
        }
//        未登录返回登录结果，
        log.info("user not login");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
/*
       filterChain 是一个 FilterChain 对象
       doFilter(请求,响应) 是 FilterChain 类中的一个方法，用于执行过滤链中 所有 的过滤器
       可以对所有请求和响应进行处理
 */

    }

    /**
     * 判断地址是否需要拦截的方法
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
//            url 从数组循环到的地址  ，requestURI 本次请求地址
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match){
                return true;
            }
        }
        return false;
    }
}
