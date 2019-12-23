package org.cny.yurayura.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * 上传文件 config
 *
 * @author CNY
 * @date 2019年12月24日 2:17
 */
@Configuration
public class UploadFileConfig {

    @Value("${yurayura.file.upload-path}")
    private String uploadPath;

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(uploadPath);
        // 设置单个文件的大小
        factory.setMaxFileSize(DataSize.ofMegabytes(10));
        // 设置总上传的数据大小
        factory.setMaxRequestSize(DataSize.ofMegabytes(10));
        return factory.createMultipartConfig();
    }
}
