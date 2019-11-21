package org.cny.yurayura.component.aspect;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.cny.yurayura.vo.ApiResult;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 防止重复提交 aspect
 *
 * @author CNY
 * @date 2019年11月14日 2:16
 */
@Aspect
@Order(1)
@Component
@Slf4j
public class PreventRepeatSubmitAspect {
    private static final Cache<String, Object> CACHES = CacheBuilder.newBuilder()
            // 最大缓存 100 个
            .maximumSize(100)
            // 设置缓存过期时间为2S
            .expireAfterWrite(3, TimeUnit.SECONDS)
            .build();

    @Around("execution(public * *(..)) && @annotation(org.cny.yurayura.annotation.PreventRepeatSubmit)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        String key = method.getName();
        if (!StringUtils.isEmpty(key)) {
            if (CACHES.getIfPresent(key) != null) {
                log.info("请勿重复提交");
                return ApiResult.dontReptCmt();
            }
            // 如果是第一次请求,就将key存入缓存中
            CACHES.put(key, key);
        }
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException("服务器异常");
        }
    }

}
