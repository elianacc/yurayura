package org.cny.yurayura.component.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 使用AOP统一处理Web请求日志 aspect
 *
 * @author CNY
 * @date 2019年3月28日 14:41
 */
@Aspect
@Order(2)
@Component
@Slf4j
public class WebLogAspect {

    @Pointcut("execution(public * org.cny.yurayura.controller..*(..))")
    public void webLogPointCut() {
    }

    @Before("webLogPointCut()")
    public void doBefore() {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
        // 记录下请求内容
        log.info("URL : " + (request != null ? request.getRequestURL().toString() : null));
        log.info("HTTP_METHOD : " + (request != null ? request.getMethod() : null));
        log.info("IP : " + (request != null ? request.getRemoteAddr() : null));
        Enumeration<String> enu = request != null ? request.getParameterNames() : null;
        while (enu != null && enu.hasMoreElements()) {
            String name = enu.nextElement();
            log.info("name:{},value:{}", name, request.getParameter(name));
        }
    }

    @AfterReturning(returning = "ret", pointcut = "webLogPointCut()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
        log.info("RESPONSE : " + ret);
    }
}
