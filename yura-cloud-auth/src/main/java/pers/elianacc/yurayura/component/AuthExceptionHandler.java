package pers.elianacc.yurayura.component;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 认证授权捕获异常 component
 *
 * @author ELiaNaCc
 * @since 2023-03-23
 */
@RestControllerAdvice
@Order(1)
public class AuthExceptionHandler {

    /**
     * 处理未登入异常
     *
     * @param exception
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @ExceptionHandler(NotLoginException.class)
    public ApiResult<String> notLoginException(NotLoginException exception) {
        return ApiResult.notAuthentication(exception.getMessage());
    }

    /**
     * 处理未授权限异常
     *
     * @param exception
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @ExceptionHandler(NotPermissionException.class)
    public ApiResult<String> notLoginException(NotPermissionException exception) {
        return ApiResult.notAuthorization(exception.getMessage());
    }

    /**
     * 处理未授角色异常
     *
     * @param exception
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @ExceptionHandler(NotRoleException.class)
    public ApiResult<String> notLoginException(NotRoleException exception) {
        return ApiResult.notAuthorization(exception.getMessage());
    }

}
