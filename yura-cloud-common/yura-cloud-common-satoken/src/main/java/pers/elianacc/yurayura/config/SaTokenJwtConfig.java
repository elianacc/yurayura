package pers.elianacc.yurayura.config;

import cn.dev33.satoken.jwt.StpLogicJwtForStateless;
import cn.dev33.satoken.stp.StpLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Sa-Token 整合 jwt (Stateless 无状态模式) config
 *
 * @author ELiaNaCc
 * @since 2024-03-31
 */
@Configuration
public class SaTokenJwtConfig {
    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForStateless();
    }

}
