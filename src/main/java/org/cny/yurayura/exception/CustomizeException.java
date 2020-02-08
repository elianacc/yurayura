package org.cny.yurayura.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author CNY
 * @description 自定义异常
 * @date 2019年3月29日 9:29
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomizeException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 异常码
     */
    private Integer errorCode;
    /**
     * 异常信息
     */
    private String errorMsg;

}
