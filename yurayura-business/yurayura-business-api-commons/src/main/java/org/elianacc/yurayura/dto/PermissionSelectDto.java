package org.elianacc.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 系统权限查询 dto
 *
 * @author ELiaNaCc
 * @since 2021-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "系统权限查询dto")
public class PermissionSelectDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限编码
     */
    @ApiModelProperty(value = "权限编码")
    private String permissionCode;

    /**
     * 权限类型- 1：菜单，2：按钮
     */
    @ApiModelProperty(value = "权限类型- 1：菜单，2：按钮", example = "1")
    private Integer permissionType;

    /**
     * 状态- 0：禁用，1：启用
     */
    @ApiModelProperty(value = "状态- 0：禁用，1：启用", example = "1")
    private Integer permissionStatus;

    /**
     * 所属子菜单标识
     */
    @ApiModelProperty(value = "所属子菜单标识")
    private String permissionBelongSubmenuName;

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码", required = true, example = "1")
    private Integer pageNum;

    /**
     * 页记录数
     */
    @ApiModelProperty(value = "页记录数", example = "10")
    private Integer pageSize;

}
