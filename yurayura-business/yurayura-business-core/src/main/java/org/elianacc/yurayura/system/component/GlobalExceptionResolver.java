package org.elianacc.yurayura.system.component;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.elianacc.yurayura.bo.MailBo;
import org.elianacc.yurayura.system.exception.RepeatSubmitException;
import org.elianacc.yurayura.system.mail.MailService;
import org.elianacc.yurayura.vo.ApiResult;
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
 * 全局捕获异常（500） component
 *
 * @author ELiaNaCc
 * @since 2019-3-29
 */
@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    @Autowired
    private MailService mailService;

    @Value("${yurayura.receive-email}")
    private String receiveEmail;

    @SneakyThrows
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception exception) {

        String errorMsg; // 错误信息

        // 异常为重复提交异常
        if (exception instanceof RepeatSubmitException) {
            RepeatSubmitException repeatSubmitException = (RepeatSubmitException) exception;
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(ApiResult.warn(repeatSubmitException.getErrorMsg())));
            return new ModelAndView();
        } else {
            // 异常为系统异常
            errorMsg = "系统异常 - " + ExceptionUtils.getStackTrace(exception);
            // 发送错误信息邮件
            MailBo bo = new MailBo();
            bo.setTitle("yurayura报错信息");
            bo.setReceiveEmail(receiveEmail);
            Map<String, Object> map = new HashMap<>();
            map.put("errorMsg", errorMsg);
            bo.setAnnexOrData(map);
            mailService.sendTemplateMail(bo, "error/500");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(ApiResult.fail("请求接口异常，详情已发送至elianacc@126.com")));
            return new ModelAndView();
        }

    }

}
