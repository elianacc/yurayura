package pers.elianacc.yurayura.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * 静态资源访问处理器 config
 *
 * @author ELiaNaCc
 * @since 2019-12-24
 */
@Configuration
public class ResourceHandlersConfig implements WebFluxConfigurer {

    @Value("${yurayura.upload-file.virtual-path}")
    private String virtualPath;
    @Value("${yurayura.upload-file.real-path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 番剧图片真实上传位置映射虚拟路径
        registry.addResourceHandler(virtualPath + "/comicImg/**")
                .addResourceLocations("file:" + uploadPath + "/comicImg/");
        // 用户头像真实上传位置映射虚拟路径
        registry.addResourceHandler(virtualPath + "/userAvatar/**")
                .addResourceLocations("file:" + uploadPath + "/userAvatar/");
    }
}
