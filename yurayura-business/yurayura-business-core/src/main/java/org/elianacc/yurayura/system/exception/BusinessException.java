package org.elianacc.yurayura.system.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 自定义业务 exception
 *
 * @author ELiaNaCc
 * @since 2019-3-29
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 异常码
     */
    private Integer errorCode;
    /**
     * 异常信息
     */
    private String errorMsg;
    /**
     * 异常详情
     */
    private String errorDetail;

}
