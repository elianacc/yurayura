package org.cny.yurayura.enumerate;

import lombok.Getter;

/**
 * 系统管理员状态 enum
 *
 * @author CNY
 * @since 2019-12-30
 */
@Getter
public enum ManagerStatusEnum {

    /**
     * 禁用
     */
    DISABLE(0),
    /**
     * 启用
     */
    ENABLE(1);

    private Integer statusId;

    ManagerStatusEnum(int statusId) {
        this.statusId = statusId;
    }

}
