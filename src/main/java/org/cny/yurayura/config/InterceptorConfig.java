package org.cny.yurayura.config;

import org.cny.yurayura.component.interceptor.BackstageInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置类 config
 *
 * @author CNY
 * @date 2019年4月2日 17:33
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private BackstageInterceptor backstageInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(backstageInterceptor).addPathPatterns("/manage/comic_info");
    }
}
