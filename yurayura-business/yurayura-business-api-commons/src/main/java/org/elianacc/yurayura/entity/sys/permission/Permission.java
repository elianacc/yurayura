package org.elianacc.yurayura.entity.sys.permission;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 系统权限 entity
 *
 * @author ELiaNaCc
 * @since 2021-08-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("yurayura_sys_permission")
@ApiModel(value = "Permission对象", description = "系统权限")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 权限编码
     */
    @ApiModelProperty(value = "权限编码")
    private String permissionCode;

    /**
     * 权限名称
     */
    @ApiModelProperty(value = "权限名称")
    private String permissionName;

    /**
     * 权限类型- 1：菜单，2：按钮
     */
    @ApiModelProperty(value = "权限类型- 1：菜单，2：按钮")
    private Integer permissionType;

    /**
     * 状态- 0：禁用，1：启用
     */
    @ApiModelProperty(value = "状态- 0：禁用，1：启用")
    private Integer permissionStatus;

    /**
     * 所属子菜单标识
     */
    @ApiModelProperty(value = "所属子菜单标识")
    private String permissionBelongSubmenuName;

    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private Integer permissionSeq;


}
