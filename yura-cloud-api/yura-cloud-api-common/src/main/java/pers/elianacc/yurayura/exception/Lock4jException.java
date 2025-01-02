package pers.elianacc.yurayura.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Lock4j exception
 *
 * @author ELiaNaCc
 * @since 2022-03-15
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lock4jException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 异常信息
     */
    private String errorMsg;
}
