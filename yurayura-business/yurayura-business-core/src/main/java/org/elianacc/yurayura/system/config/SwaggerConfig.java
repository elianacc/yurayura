package org.elianacc.yurayura.system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2 config
 *
 * @author ELiaNaCc
 * @since 2019-11-18
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${yurayura.swagger.enable}")
    private Boolean swaggerEnable;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.elianacc.yurayura.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        .title("YuraYura-Business-Servive API")
                        .description("网站B端在线接口文档")
                        .version("1.0.2-SNAPSHOT")
                        .contact(new Contact("EliaNaCc", "https://github.com/elianacc/yurayura-business"
                                , "438507003@qq.com"))
                        .license("Apache License")
                        .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                        .build()).enable(swaggerEnable);
    }
}
