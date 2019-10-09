package com.scs.top.project.framework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


/**
 * 后端跨域配置
 * @author yihur
 */
@Configuration
public class CORSConfiguration implements WebMvcConfigurer {

    /**
     * 跨域支持
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .maxAge(3600 * 24)
                .allowedHeaders("*")
                .allowedMethods("*");
    }



}
