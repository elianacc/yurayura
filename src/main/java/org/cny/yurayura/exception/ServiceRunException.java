package org.cny.yurayura.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author CNY
 * @description 业务进行时异常
 * @date 2019年3月29日 9:29
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRunException extends RuntimeException {

    private Integer runStatus;
    private String runMessage;

    private static final long serialVersionUID = 1L;

    public ServiceRunException(Integer runStatus, String runMessage, Throwable cause) {
        super(cause);
        this.runStatus = runStatus;
        this.runMessage = runMessage;
    }

}
