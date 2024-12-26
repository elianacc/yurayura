package pers.elianacc.yurayura.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 前端vue-router vo
 *
 * @author ELiaNaCc
 * @since 2024-12-25
 */
@Data
@ApiModel(value = "前端vue-router VO", description = "前端vue-router视图对象")
public class VueRouterVO {

    private static final long serialVersionUID = 1L;

    /**
     * 路径
     */
    @ApiModelProperty(value = "路径")
    private String path;

    /**
     * 组件名
     */
    @ApiModelProperty(value = "组件名")
    private String name;

    /**
     * 组件
     */
    @ApiModelProperty(value = "组件")
    private String component;

}
