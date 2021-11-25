package org.elianacc.yurayura.system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 处理跨域 config
 *
 * @author ELiaNaCc
 * @since 2020-11-26
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**") // 所有的当前站点的请求地址，都支持跨域访问
                .allowedOrigins("*") // 所有的外部域都可跨域访问
                .allowCredentials(true) // 是否支持跨域用户凭证
                .allowedHeaders(CorsConfiguration.ALL) // 当前站点支持的跨域请求头是什么
                .allowedMethods(CorsConfiguration.ALL) // 当前站点支持的跨域请求类型是什么
                .maxAge(3600); // 超时时长设置为1小时，时间单位是秒
    }
}
