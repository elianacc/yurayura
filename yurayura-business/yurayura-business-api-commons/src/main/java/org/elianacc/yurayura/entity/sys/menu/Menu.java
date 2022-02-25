package org.elianacc.yurayura.entity.sys.menu;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统菜单 entity
 *
 * @author ELiaNaCc
 * @since 2021-03-16
 */
@Data
@TableName("yurayura_sys_menu")
@ApiModel(value = "Menu对象", description = "系统菜单")
public class Menu implements Serializable {

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
    @ApiModelProperty(value = "标题")
    private String menuTitle;

    /**
     * 标识
     */
    @ApiModelProperty(value = "标识")
    private String menuName;

    /**
     * 图标样式
     */
    @ApiModelProperty(value = "图标样式")
    private String menuIconClass;

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
