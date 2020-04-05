package org.cny.yurayura.config;

import org.cny.yurayura.system.interceptor.BusinessPageInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器 config
 *
 * @author CNY
 * @since 2019-4-2
 */
@Configuration
public class InterceptorsConfig implements WebMvcConfigurer {

    @Autowired
    private BusinessPageInterceptor businessPageInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(businessPageInterceptor)
                .addPathPatterns("/business/comic_info", "/business/sys_dict", "/business/user_info");
    }
}
