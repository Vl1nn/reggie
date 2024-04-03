package com.ann.reggie.filter;

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
//    路径匹配器 支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        向下转型
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        
//        获取本次请求地址
        String requestURI = request.getRequestURI();
//        添加放行地址
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
//                放行所有静态资源
                "/backend/**",
                "/front/**",
            };

        filterChain.doFilter(request,response);
    }

    /**
     * 检查提供的URL数组中是否至少有一个URL与指定的请求URI匹配。
     *
     * @param urls 一个包含多个URL的字符串数组。
     * @param requestURI 需要与URL进行匹配的请求URI字符串。
     * @return 如果至少有一个URL与请求URI匹配，则返回true；否则返回false。
     */
    public boolean check(String[] urls,String requestURI){
        // 遍历提供的URL数组，检查每个URL是否与请求URI匹配
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match){
                // 如果找到匹配的URL，则返回true
                return true;
            }
        }
        // 如果没有URL与请求URI匹配，则返回false
        return false;
    }
}
