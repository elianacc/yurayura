package org.cny.yurayura.enumerate;

import lombok.Getter;

/**
 * 管理员状态枚举
 *
 * @author CNY
 * @date 2019年12月30日 16:03
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
