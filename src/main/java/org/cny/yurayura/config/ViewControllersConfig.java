package org.cny.yurayura.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 视图控制器 config
 *
 * @author CNY
 * @since 2020-01-03
 */
@Configuration
public class ViewControllersConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/business/manager_login").setViewName("business/manager_login");
        registry.addViewController("/business/manager_unlogin").setViewName("business/manager_unlogin");
        registry.addViewController("/business/sys_dict").setViewName("business/sys_dict");
        registry.addViewController("/business/comic_info").setViewName("business/comic_info");
    }
}
