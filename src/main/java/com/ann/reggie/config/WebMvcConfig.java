package com.ann.reggie.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /**
     * 设置静态资源映射
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        后台页面
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
//        前台页面
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }
}
