package org.elianacc.yurayura.enumerate;

import lombok.Getter;

/**
 * 启用-禁用状态 enum
 *
 * @author ELiaNaCc
 * @since 2021-03-25
 */
@Getter
public enum EnableStatusEnum {

    /**
     * 禁用
     */
    DISABLE(0),
    /**
     * 启用
     */
    ENABLE(1);

    private Integer statusId;

    EnableStatusEnum(int statusId) {
        this.statusId = statusId;
    }

}
