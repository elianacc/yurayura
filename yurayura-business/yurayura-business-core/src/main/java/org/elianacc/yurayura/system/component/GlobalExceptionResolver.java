package org.elianacc.yurayura.system.component;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.elianacc.yurayura.bo.MailBo;
import org.elianacc.yurayura.system.exception.CustomizeException;
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
@Slf4j
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

        boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With")); // 判断请求是否是ajax请求
        String errorMsg; // 错误信息

        // 异常为自定义异常
        if (exception instanceof CustomizeException) {
            CustomizeException customizeException = (CustomizeException) exception;

            errorMsg = "自定义异常 - " + "errorCode:" + customizeException.getErrorCode()
                    + ",errorMsg:" + customizeException.getErrorMsg()
                    + "\r\n"
                    + "errorDetail:" + customizeException.getErrorDetail();
        } else {
            // 异常为系统异常
            errorMsg = "系统异常 - " + ExceptionUtils.getStackTrace(exception);
        }
        log.error(errorMsg);

        // 发送错误信息邮件
        MailBo bo = new MailBo();
        bo.setTitle("yurayura报错信息");
        bo.setReceiveEmail(receiveEmail);
        Map<String, Object> map = new HashMap<>();
        map.put("errorMsg", errorMsg);
        bo.setAnnexOrData(map);
        mailService.sendTemplateMail(bo, "error/500");

        if (isAjax) { // ajax请求下的异常
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(ApiResult.fail("请求接口异常，请联系开发人员！")));
            return new ModelAndView();
        } else { // 页面请求下的异常
            response.setContentType("text/html;charset=UTF-8");
            // 输出错误信息到自定义的500页面
            ModelAndView mv = new ModelAndView();
            mv.addObject("errorMsg", errorMsg);
            mv.setViewName("error/500");
            return mv;
        }

    }

}
