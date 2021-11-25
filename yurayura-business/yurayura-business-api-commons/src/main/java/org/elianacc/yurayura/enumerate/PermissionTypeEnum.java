package org.elianacc.yurayura.enumerate;

import lombok.Getter;

/**
 * 权限类型 enum
 *
 * @author ELiaNaCc
 * @since 2021-03-25
 */
@Getter
public enum PermissionTypeEnum {

    /**
     * 菜单
     */
    MENU(1),
    /**
     * 按钮
     */
    BUTTON(2);

    private Integer typeId;

    PermissionTypeEnum(int typeId) {
        this.typeId = typeId;
    }

}
