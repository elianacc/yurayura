package org.cny.yurayura.enumerate;

import lombok.Getter;

/**
 * 系统数据字典状态 enum
 *
 * @author CNY
 * @since 2020-03-25
 */
@Getter
public enum DictStatusEnum {

    /**
     * 禁用
     */
    DISABLE(0),
    /**
     * 启用
     */
    ENABLE(1);

    private Integer statusId;

    DictStatusEnum(int statusId) {
        this.statusId = statusId;
    }


}
