package org.cny.yurayura.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 通用返回信息处理 vo
 *
 * @author CNY
 * @since 2019年1月1日 14:16
 */
@Getter
@Setter
public class ApiResult {

    private final static int SUCCESS_CODE = 200; // 服务器处理成功状态码
    private final static int WARN_CODE = 100; // 服务器警告状态码
    private final static int FAIL_CODE = 500; // 服务器错误状态码
    private final static int DONT_REPT_SUBMIT_CODE = 101; // 不允许重复提交状态码

    private final static String DONT_REPT_SUBMIT_MSG = "请勿重复提交"; // 不允许重复提交提示信息

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

    public static ApiResult dontReptSubmit() {
        ApiResult result = new ApiResult();
        result.setCode(DONT_REPT_SUBMIT_CODE);
        result.setMsg(DONT_REPT_SUBMIT_MSG);
        return result;
    }

}
