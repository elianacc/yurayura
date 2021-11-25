package org.elianacc.yurayura.entity.sys.menu;

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
 * 系统子菜单 entity
 *
 * @author ELiaNaCc
 * @since 2021-03-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("yurayura_sys_menu_sub")
@ApiModel(value = "MenuSub对象", description = "系统子菜单")
public class MenuSub implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题", required = true)
    private String menuTitle;

    /**
     * 标识
     */
    @ApiModelProperty(value = "标识", required = true)
    private String menuName;

    /**
     * 路径
     */
    @ApiModelProperty(value = "路径", required = true)
    private String menuIndex;

    /**
     * 图标样式
     */
    @ApiModelProperty(value = "图标样式", required = true)
    private String menuIconClass;

    /**
     * 父菜单id
     */
    @ApiModelProperty(value = "父菜单id", required = true)
    private Integer menuPid;

    /**
     * 类型- 1：一级菜单，2：二级菜单
     */
    @ApiModelProperty(value = "类型- 1：一级菜单，2：二级菜单")
    private Integer menuType;

    /**
     * 序号
     */
    @ApiModelProperty(value = "序号", required = true)
    private Integer menuSeq;

    /**
     * 状态- 0：隐藏，1：显示
     */
    @ApiModelProperty(value = "状态- 0：隐藏，1：显示", required = true)
    private Integer menuStatus;

}
