package pers.elianacc.yurayura.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统子菜单树形展示 vo
 *
 * @author ELiaNaCc
 * @since 2024-09-09
 */
@Data
@ApiModel(value = "系统子菜单树形展示VO", description = "系统子菜单树形展示视图对象")
public class SysMenuSubVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String menuTitle;

    /**
     * 标识
     */
    @ApiModelProperty(value = "标识")
    private String menuName;

    /**
     * 路径
     */
    @ApiModelProperty(value = "路径")
    private String menuIndex;

    /**
     * 图标样式
     */
    @ApiModelProperty(value = "图标样式")
    private String menuIconClass;

    /**
     * 父菜单id
     */
    @ApiModelProperty(value = "父菜单id")
    private Integer menuPid;

    /**
     * 类型- 1：一级菜单，2：二级菜单
     */
    @ApiModelProperty(value = "类型- 1：一级菜单，2：二级菜单")
    private Integer menuType;

    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private Integer menuSeq;

    /**
     * 状态- 0：隐藏，1：显示
     */
    @ApiModelProperty(value = "状态- 0：隐藏，1：显示")
    private Integer menuStatus;

}
