package org.cny.yurayura.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 权限授权-树形控件查询 bo
 *
 * @author CNY
 * @since 2021-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PermissionAuthorTreeSelectBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Double id;

    /**
     * 子菜单标题
     */
    private String title;

    /**
     * 权限列表
     */
    private List<PermissionAuthorPermBo> permissionList;
}
