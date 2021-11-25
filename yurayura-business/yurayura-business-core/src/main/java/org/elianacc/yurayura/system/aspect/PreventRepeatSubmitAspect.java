package org.elianacc.yurayura.system.aspect;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.elianacc.yurayura.system.exception.CustomizeException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 防止重复提交 aspect
 *
 * @author ELiaNaCc
 * @since 2019-11-14
 */
@Aspect
@Order(1)
@Component
@Slf4j
public class PreventRepeatSubmitAspect {
    private static final Cache<String, Object> CACHES = CacheBuilder.newBuilder()
            // 最大缓存 100 个
            .maximumSize(100)
            // 设置缓存过期时间为3S
            .expireAfterWrite(3, TimeUnit.SECONDS)
            .build();

    @Around("execution(public * *(..)) && @annotation(org.elianacc.yurayura.system.annotation.PreventRepeatSubmit)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String sessionId = Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).getSessionId();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        // 提交key为sessionId+url
        String key = sessionId + "-" + request.getServletPath();
        if (ObjectUtils.isEmpty(key)) {
            log.error("提交key为空！");
            return new CustomizeException(500, "提交进程异常！", "提交的key（sessionId+请求url）为空");
        }
        if (CACHES.getIfPresent(key) != null) {
            log.warn("请勿重复提交！");
        }
        // 如果是第一次请求,就将key存入缓存中
        CACHES.put(key, key);
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new CustomizeException(500, "提交进程异常！", ExceptionUtils.getStackTrace(throwable));
        }
    }
}
