package org.elianacc.yurayura.system.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;

/**
 * 使用AOP统一处理Web请求日志 aspect
 *
 * @author ELiaNaCc
 * @since 2019-3-28
 */
@Aspect
@Order(6)
@Component
@Slf4j
public class WebLogAspect {

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * web日志切入点（controller包下定义的所有请求）
     */
    @Pointcut("execution(public * org.elianacc.yurayura.controller..*(..))")
    public void webLogPointCut() {
    }

    @Before("webLogPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        // 记录请求内容
        log.info("========================================== Start ==========================================");
        log.info("URL            : {}", request.getRequestURL().toString());
        log.info("HTTP METHOD    : {}", request.getMethod());
        log.info("IP             : {}", request.getRemoteAddr());
        log.info("CLASS METHOD   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.info("REQUEST ARGS   : {}", Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "webLogPointCut()")
    public void doAfterReturning(Object ret) {
        // 记录返回内容
        log.info("RESPONSE       : {}", ret);
        log.info("TIME CONSUMING : {} ms", System.currentTimeMillis() - startTime.get());
        log.info("=========================================== End ===========================================\r\n\r\n");
        startTime.remove();
    }

}
