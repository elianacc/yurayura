package pers.elianacc.yurayura.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 系统菜单树形展示 vo
 *
 * @author ELiaNaCc
 * @since 2021-03-16
 */
@Data
@ApiModel(value = "系统菜单树形展示VO", description = "系统菜单树形展示视图对象")
public class SysMenuTreeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("id")
    private Integer id;

    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String menuTitle;

    /**
     * 标识
     */
    @ApiModelProperty("标识")
    private String menuName;

    /**
     * 图标样式
     */
    @ApiModelProperty("图标样式")
    private String menuIconClass;

    /**
     * 类型- 1：一级菜单，2：二级菜单
     */
    @ApiModelProperty("类型- 1：一级菜单，2：二级菜单")
    private Integer menuType;

    /**
     * 序号
     */
    @ApiModelProperty("序号")
    private Integer menuSeq;

    /**
     * 状态- 0：禁用，1：启用
     */
    @ApiModelProperty("状态- 0：禁用，1：启用")
    private Integer menuStatus;

    /**
     * 子菜单列表
     */
    @ApiModelProperty("子菜单列表")
    private List<SysMenuSubVO> menuSubList;

}
