package org.elianacc.yurayura.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 通用返回信息处理 vo
 *
 * @author ELiaNaCc
 * @since 2019-1-1
 */
@Getter
@Setter
public class ApiResult {

    private final static int SUCCESS_CODE = 200; // 服务器处理成功状态码
    private final static int FAIL_CODE = 500; // 服务器错误状态码
    private final static int WARN_CODE = 102; // 服务器警告状态码

    private final static int NOT_AUTHENTICATION = 401; // 未认证
    private final static int NOT_AUTHORIZATION = 405; // 未授权

    private final static String NOT_AUTHENTICATION_MSG = "登入认证过期，请重新登入"; // 未认证提示信息
    private final static String NOT_AUTHORIZATION_MSG = "还未授权此操作"; // 未授权提示信息

    // 状态码
    private Integer code;
    // 用户要返回给浏览器的数据
    private Object data;
    // 提示信息
    private String msg;

    public static ApiResult success() {
        ApiResult result = new ApiResult();
        result.setCode(SUCCESS_CODE);
        return result;
    }

    public static ApiResult success(String msg) {
        ApiResult result = new ApiResult();
        result.setCode(SUCCESS_CODE);
        result.setMsg(msg);
        return result;
    }

    public static ApiResult success(String msg, Object data) {
        ApiResult result = new ApiResult();
        result.setCode(SUCCESS_CODE);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    public static ApiResult warn() {
        ApiResult result = new ApiResult();
        result.setCode(WARN_CODE);
        return result;
    }

    public static ApiResult warn(String msg) {
        ApiResult result = new ApiResult();
        result.setCode(WARN_CODE);
        result.setMsg(msg);
        return result;
    }

    public static ApiResult fail() {
        ApiResult result = new ApiResult();
        result.setCode(FAIL_CODE);
        return result;
    }

    public static ApiResult fail(String msg) {
        ApiResult result = new ApiResult();
        result.setCode(FAIL_CODE);
        result.setMsg(msg);
        return result;
    }

    public static ApiResult notAuthentication() {
        ApiResult result = new ApiResult();
        result.setCode(NOT_AUTHENTICATION);
        result.setMsg(NOT_AUTHENTICATION_MSG);
        return result;
    }

    public static ApiResult notAuthorization() {
        ApiResult result = new ApiResult();
        result.setCode(NOT_AUTHORIZATION);
        result.setMsg(NOT_AUTHORIZATION_MSG);
        return result;
    }

}
