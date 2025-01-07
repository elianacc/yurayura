package pers.elianacc.yurayura.enumerate;

import lombok.Getter;

/**
 * 超管组织 enum
 *
 * @author ELiaNaCc
 * @since 2025-01-07
 */
@Getter
public enum AdminOrgEnum {

    /**
     * 超管组织
     */
    ADMIN_ORG(0);

    private Integer org;

    AdminOrgEnum(Integer org) {
        this.org = org;
    }
}
