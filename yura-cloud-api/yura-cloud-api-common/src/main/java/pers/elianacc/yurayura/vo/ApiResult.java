package pers.elianacc.yurayura.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 接口返回 vo
 *
 * @author ELiaNaCc
 * @since 2023-02-16
 */
@Data
@ApiModel(value = "接口返回VO", description = "接口返回对象")
public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public final static int SUCCESS_CODE = 200; // 服务器处理成功状态码
    public final static int FAIL_CODE = 500; // 服务器错误状态码

    public final static int NOT_AUTHENTICATION = 401; // 未认证
    public final static int NOT_AUTHORIZATION = 405; // 未授权
    public final static int FORBIDDEN = 403; // 服务器理解请求客户端的请求，但是拒绝执行此请求
    public final static int BAD_REQUEST = 400; // 客户端请求的语法错误，服务器无法理解

    /**
     * 状态码
     */
    @ApiModelProperty("状态码")
    private Integer code;
    /**
     * 返回体
     */
    @ApiModelProperty("返回体")
    private T data;
    /**
     * 返回信息
     */
    @ApiModelProperty("返回信息")
    private String msg;
    /**
     * 时间戳
     */
    @ApiModelProperty("时间戳")
    private long timestamp = System.currentTimeMillis();

    public static <T> ApiResult<T> success() {
        ApiResult<T> r = new ApiResult();
        r.setCode(SUCCESS_CODE);
        return r;
    }

    public static <T> ApiResult<T> success(String msg) {
        ApiResult<T> r = new ApiResult();
        r.setCode(SUCCESS_CODE);
        r.setMsg(msg);
        return r;
    }

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> r = new ApiResult();
        r.setCode(SUCCESS_CODE);
        r.setData(data);
        return r;
    }

    public static <T> ApiResult<T> success(String msg, T data) {
        ApiResult<T> r = new ApiResult();
        r.setCode(SUCCESS_CODE);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static <T> ApiResult<T> warn() {
        ApiResult<T> r = new ApiResult();
        r.setCode(FORBIDDEN);
        return r;
    }

    public static <T> ApiResult<T> warn(String msg) {
        ApiResult<T> r = new ApiResult();
        r.setCode(FORBIDDEN);
        r.setMsg(msg);
        return r;
    }

    public static <T> ApiResult<T> fail() {
        ApiResult<T> r = new ApiResult();
        r.setCode(FAIL_CODE);
        return r;
    }

    public static <T> ApiResult<T> fail(String msg) {
        ApiResult<T> r = new ApiResult();
        r.setCode(FAIL_CODE);
        r.setMsg(msg);
        return r;
    }

    public static <T> ApiResult<T> notAuthentication(String msg) {
        ApiResult<T> r = new ApiResult();
        r.setCode(NOT_AUTHENTICATION);
        r.setMsg(msg);
        return r;
    }

    public static <T> ApiResult<T> notAuthorization(String msg) {
        ApiResult<T> r = new ApiResult();
        r.setCode(NOT_AUTHORIZATION);
        r.setMsg(msg);
        return r;
    }

    public static <T> ApiResult<T> badRequest(String msg) {
        ApiResult<T> r = new ApiResult();
        r.setCode(BAD_REQUEST);
        r.setMsg(msg);
        return r;
    }

}
