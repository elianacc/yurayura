package org.elianacc.yurayura.system.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 重复提交 exception
 *
 * @author ELiaNaCc
 * @since 2022-03-15
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepeatSubmitException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 异常信息
     */
    private String errorMsg;
}
