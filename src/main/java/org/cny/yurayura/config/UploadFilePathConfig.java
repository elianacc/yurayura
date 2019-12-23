package org.cny.yurayura.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 上传文件虚拟路径 config
 *
 * @author CNY
 * @date 2019年12月24日 2:31
 */
@Configuration
public class UploadFilePathConfig implements WebMvcConfigurer {

    @Value("${yurayura.file.virtual-path}")
    private String virtualPath;
    @Value("${yurayura.file.upload-path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(virtualPath).addResourceLocations("file:" + uploadPath);
    }
}
