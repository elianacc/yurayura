package org.elianacc.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统菜单修改 dto
 *
 * @author ELiaNaCc
 * @since 2022-02-23
 */
@Data
@ApiModel(value = "系统菜单修改dto")
public class MenuUpdateDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Integer id;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题", required = true)
    private String menuTitle;

    /**
     * 图标样式
     */
    @ApiModelProperty(value = "图标样式", required = true)
    private String menuIconClass;

    /**
     * 序号
     */
    @ApiModelProperty(value = "序号", required = true, example = "1")
    private Integer menuSeq;

    /**
     * 状态- 0：隐藏，1：显示
     */
    @ApiModelProperty(value = "状态- 0：隐藏，1：显示", required = true, example = "1")
    private Integer menuStatus;

}
