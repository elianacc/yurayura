package org.cny.yurayura.enumerate;

import lombok.Getter;

/**
 * 系统菜单类型 enum
 *
 * @author CNY
 * @since 2021-03-25
 */
@Getter
public enum MenuTypeEnum {

    /**
     * 一级菜单
     */
    FIRSTLEVEL(1),
    /**
     * 二级菜单
     */
    SECONDLEVEL(2);

    private Integer typeId;

    MenuTypeEnum(int typeId) {
        this.typeId = typeId;
    }

}
