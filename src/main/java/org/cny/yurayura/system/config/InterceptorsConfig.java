package org.cny.yurayura.system.config;

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
                .addPathPatterns("/business/comic_info", "/business/sys_dict", "/business/user_info", "/comic/getPage4B",
                        "/comic/insert", "/comic/update", "/comic/deleteBatchByIds", "/comic/getById", "/sys/dict/getPage",
                        "/sys/dict/insert", "/sys/dict/update", "/sys/dict/deleteBatchByIds", "/sys/dict/getById");
    }
}
