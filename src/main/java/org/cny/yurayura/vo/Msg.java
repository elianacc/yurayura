package org.cny.yurayura.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 通用返回信息处理 vo
 *
 * @author CNY
 * @date 2019年1月1日 14:16
 */
@Getter
@Setter
public class Msg {
    // 状态码
    // 200-服务器处理成功
    // 500-服务器出错
    // 100-服务器警告
    // 101-重复提交
    private Integer code;
    // 用户要返回给浏览器的数据
    private Object data;
    // 提示信息
    private String msg;

    public static Msg success() {
        Msg result = new Msg();
        result.setCode(200);
        return result;
    }

    public static Msg success(String msg) {
        Msg result = new Msg();
        result.setCode(200);
        result.setMsg(msg);
        return result;
    }

    public static Msg success(String msg, Object data) {
        Msg result = new Msg();
        result.setCode(200);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    public static Msg warn() {
        Msg result = new Msg();
        result.setCode(100);
        return result;
    }

    public static Msg warn(String msg) {
        Msg result = new Msg();
        result.setCode(100);
        result.setMsg(msg);
        return result;
    }

    public static Msg dontReptCmt() {
        Msg result = new Msg();
        result.setCode(101);
        return result;
    }

    public static Msg dontReptCmt(String msg) {
        Msg result = new Msg();
        result.setCode(101);
        result.setMsg(msg);
        return result;
    }

    public static Msg fail() {
        Msg result = new Msg();
        result.setCode(500);
        return result;
    }

    public static Msg fail(String msg) {
        Msg result = new Msg();
        result.setCode(500);
        result.setMsg(msg);
        return result;
    }

}
