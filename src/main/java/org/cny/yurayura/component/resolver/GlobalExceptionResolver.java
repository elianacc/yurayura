package org.cny.yurayura.component.resolver;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.cny.yurayura.dto.MailDTO;
import org.cny.yurayura.exception.CustomizeException;
import org.cny.yurayura.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局捕获异常（500） resolver
 *
 * @author CNY
 * @date 2019年3月29日 9:12
 */
@Slf4j
@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    @Autowired
    private MailService mailService;

    @Value("${yurayura.receive-email}")
    private String receiveEmail;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception exception) {

        response.setContentType("text/html;charset=UTF-8");
        String errorMsg;

        // 异常为自定义异常
        if (exception instanceof CustomizeException) {
            CustomizeException customizeException = (CustomizeException) exception;

            errorMsg = "自定义异常 - " + "errorCode:" + customizeException.getErrorCode() + ",errorMsg:" + customizeException.getErrorMsg() + "\r\n"
                    + ExceptionUtils.getStackTrace(customizeException);
        } else {
            // 异常为系统异常
            errorMsg = "系统异常 - " + ExceptionUtils.getStackTrace(exception);
        }
        log.error(errorMsg);

        // 发送错误信息邮件
        MailDTO dto = new MailDTO();
        dto.setTitle("yurayura报错信息");
        dto.setReceiveEmail(receiveEmail);
        Map<String, Object> map = new HashMap<>();
        map.put("errorMsg", errorMsg);
        dto.setAnnexOrData(map);
        mailService.sendTemplateMail(dto, "error/500");

        // 输出错误信息到自定义的500页面
        ModelAndView mv = new ModelAndView();
        mv.addObject("errorMsg", errorMsg);
        mv.setViewName("error/500");
        return mv;

    }

}
