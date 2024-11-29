package pers.elianacc.yurayura.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 权限授权-树形控件 vo
 *
 * @author ELiaNaCc
 * @since 2021-08-14
 */
@Data
@ApiModel(value = "权限授权-树形控件VO", description = "权限授权-树形控件视图对象")
public class SysPermissionAuthorTreeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("id")
    private Double id;

    /**
     * 子菜单标题
     */
    @ApiModelProperty("子菜单标题")
    private String title;

    /**
     * 权限列表
     */
    @ApiModelProperty("权限列表")
    private List<SysPermissionAuthorPermVO> permissionList;
}
