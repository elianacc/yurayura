package org.elianacc.yurayura.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.FlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * spring session（redis方案） config
 *
 * @author ELiaNaCc
 * @since 2021-09-22
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 60 * 24, flushMode = FlushMode.ON_SAVE, redisNamespace = "yurayura-business-service-session")
public class RedisSessionConfig {

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
        // 设置cookie过期时间为1天
        defaultCookieSerializer.setCookieMaxAge(60 * 60 * 24);
        // cookie名字 默认是 "SESSION"
        defaultCookieSerializer.setCookieName("YuraYuraBusinessServiceSession");
        // 存储路径 默认是 "/"
        defaultCookieSerializer.setCookiePath("/");
        return defaultCookieSerializer;
    }

}
