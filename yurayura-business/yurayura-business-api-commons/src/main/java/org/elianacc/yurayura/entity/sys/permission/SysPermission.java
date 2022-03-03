package org.elianacc.yurayura.entity.sys.permission;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统权限 entity
 *
 * @author ELiaNaCc
 * @since 2022-02-26
 */
@Data
@TableName("yurayura_sys_permission")
@ApiModel(value = "Permission对象", description = "系统权限")
public class SysPermission implements Serializable {

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
    @TableField("permission_code")
    @ApiModelProperty(value = "权限编码")
    private String permissionCode;

    /**
     * 权限名称
     */
    @TableField("permission_name")
    @ApiModelProperty(value = "权限名称")
    private String permissionName;

    /**
     * 权限类型- 1：菜单，2：按钮
     */
    @TableField("permission_type")
    @ApiModelProperty(value = "权限类型- 1：菜单，2：按钮")
    private Integer permissionType;

    /**
     * 状态- 0：禁用，1：启用
     */
    @TableField("permission_status")
    @ApiModelProperty(value = "状态- 0：禁用，1：启用")
    private Integer permissionStatus;

    /**
     * 所属子菜单标识
     */
    @TableField("permission_belong_submenu_name")
    @ApiModelProperty(value = "所属子菜单标识")
    private String permissionBelongSubmenuName;

    /**
     * 序号
     */
    @TableField("permission_seq")
    @ApiModelProperty(value = "序号")
    private Integer permissionSeq;


}
