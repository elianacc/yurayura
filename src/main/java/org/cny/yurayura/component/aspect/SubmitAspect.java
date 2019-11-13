package org.cny.yurayura.component.aspect;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.cny.yurayura.annotation.Submit;
import org.cny.yurayura.vo.Msg;
import org.springframework.context.annotation.Configuration;
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
@Configuration
public class SubmitAspect {
    private static final Cache<String, Object> CACHES = CacheBuilder.newBuilder()
            // 最大缓存 100 个
            .maximumSize(100)
            // 设置缓存过期时间为3S
            .expireAfterWrite(3, TimeUnit.SECONDS)
            .build();

    @Around("execution(public * *(..)) && @annotation(org.cny.yurayura.annotation.Submit)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Submit submit = method.getAnnotation(Submit.class);
        String key = getCacheKey(submit, pjp.getArgs());
        if (!StringUtils.isEmpty(key)) {
            if (CACHES.getIfPresent(key) != null) {
                return Msg.warn("请勿重复提交");
            }
            // 如果是第一次请求,就将key存入缓存中
            CACHES.put(key, key);
        }
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException("服务器异常");
        } finally {
            //如果演示的话需要注释该代码,手动将缓存清除，实际应该放开
            CACHES.invalidate(key);
        }
    }

    /**
     * Cache key生成策略，这里可以自定义实现，比如再Submit注解中添加需要从request中获取字段名称，在此方法中通过反射获取，拼接为最终的Cache key
     * 本方法Cache key使用最简单的策略：prefix + request参数的toString，这里只做展示使用，一般不会使用这种策略，一是会导致cache key过长，浪费存储空间，
     * 二是，如果请求参数没有实现toString方法，对于相同的请求参数，依然会被认为是两个不同的请求
     */
    private String getCacheKey(Submit submit, Object[] args) {
        String prefix = submit.prefix();
        return prefix + args[0];
    }
}
