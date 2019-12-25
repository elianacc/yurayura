package org.cny.yurayura.config;

import org.cny.yurayura.component.interceptor.ManagerUnLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器 config
 *
 * @author CNY
 * @date 2019年4月2日 17:33
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private ManagerUnLoginInterceptor managerUnLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(managerUnLoginInterceptor)
                .addPathPatterns("/business/comic_info", "/comic/getPageToAll",
                        "/comic/insert", "/comic/deleteById", "/comic/deleteBatchByIds",
                        "/comic/getOneById", "/comic/update", "/comic/getPageByName");
    }
}
