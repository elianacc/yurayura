package org.cny.yurayura.system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 静态资源访问处理器 config
 *
 * @author CNY
 * @since 2019-12-24
 */
@Configuration
public class ResourceHandlersConfig implements WebMvcConfigurer {

    @Value("${yurayura.upload-file.virtual-path}")
    private String virtualPath;
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(virtualPath).addResourceLocations("file:" + uploadPath);
    }
}
