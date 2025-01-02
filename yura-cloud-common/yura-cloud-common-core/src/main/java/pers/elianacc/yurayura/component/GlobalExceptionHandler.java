package pers.elianacc.yurayura.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.elianacc.yurayura.bo.MailBO;
import pers.elianacc.yurayura.exception.BusinessException;
import pers.elianacc.yurayura.exception.Lock4jException;
import pers.elianacc.yurayura.utils.MailUtil;
import pers.elianacc.yurayura.vo.ApiResult;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 全局捕获异常 component
 *
 * @author ELiaNaCc
 * @since 2023-03-23
 */
@RestControllerAdvice
@Slf4j
@Order(2)
public class GlobalExceptionHandler {

    @Value("${yurayura.receive-email}")
    private String receiveEmail;
    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 处理入参异常（bean参数验证 带requestbody的方式）
     *
     * @param e
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult<String> validExceptionHandler(MethodArgumentNotValidException e) {
        return ApiResult.badRequest(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    /**
     * 处理入参异常（bean参数验证）
     *
     * @param e
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @ExceptionHandler(BindException.class)
    public ApiResult<String> validExceptionHandler(BindException e) {
        return ApiResult.badRequest(Objects.requireNonNull(e.getFieldError()).getDefaultMessage());
    }

    /**
     * 处理单个参数异常（带requestparam的方式）
     *
     * @param e
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResult<String> validExceptionHandler(ConstraintViolationException e) {
        String msg = e.getMessage();
        if (msg != null) {
            int lastIndex = msg.lastIndexOf(':');
            if (lastIndex >= 0) {
                msg = msg.substring(lastIndex + 1).trim();
            }
        }
        return ApiResult.badRequest(msg);
    }

    /**
     * 处理Lock4j异常
     *
     * @param lock4jException
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @ExceptionHandler(Lock4jException.class)
    public ApiResult<String> repeatSubmitExceptionHandler(Lock4jException lock4jException) {
        return ApiResult.warn(lock4jException.getErrorMsg());
    }

    /**
     * 处理业务异常
     *
     * @param businessException
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @ExceptionHandler(BusinessException.class)
    public ApiResult<String> businessExceptionHandler(BusinessException businessException) {
        if (businessException.getErrorCode() == ApiResult.FAIL_CODE) {
            return ApiResult.fail(businessException.getErrorMsg());
        } else if (businessException.getErrorCode() == ApiResult.NOT_AUTHENTICATION) {
            return ApiResult.notAuthentication(businessException.getErrorMsg());
        } else {
            return ApiResult.warn(businessException.getErrorMsg());
        }
    }

    /**
     * 处理断言异常
     *
     * @param exception
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResult<String> illegalArgumentException(IllegalArgumentException exception) {
        String msg = StringUtils.abbreviate(exception.getMessage(), 100);
        return ApiResult.warn(msg);
    }

    /**
     * 处理系统异常
     *
     * @param exception
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @ExceptionHandler(Exception.class)
    public ApiResult<String> exceptionHandler(Exception exception) {
        // 错误信息
        String errorMsg;
        // 异常为系统异常
        errorMsg = ExceptionUtils.getStackTrace(exception);
        log.error(errorMsg);
        // 发送错误信息邮件
        MailBO bo = new MailBO();
        bo.setTitle("【" + applicationName + "】服务报错信息");
        bo.setReceiveEmail(receiveEmail);
        Map<String, Object> map = new HashMap<>();
        map.put("errorMsg", errorMsg);
        bo.setAnnexOrData(map);
        MailUtil.sendTemplateMail(bo, "error/500");
        return ApiResult.fail(applicationName + "服务异常");
    }

}
