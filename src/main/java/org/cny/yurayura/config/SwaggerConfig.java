package org.cny.yurayura.config;

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
 * @author CNY
 * @since 2019年11月18日 21:57
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
                .apis(RequestHandlerSelectors.basePackage("org.cny.yurayura.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        .title("Yura-Yura API")
                        .description("Yura-Yura swagger在线api文档")
                        .version("11.0.1-SNAPSHOT")
                        .contact(new Contact("EliaNaCc", "https://github.com/elianacc/yurayura", "438507003@qq.com"))
                        .license("Apache License")
                        .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                        .build()).enable(swaggerEnable);
    }
}
