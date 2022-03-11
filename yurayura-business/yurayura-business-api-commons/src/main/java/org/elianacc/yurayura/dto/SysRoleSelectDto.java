package org.elianacc.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统角色查询 dto
 *
 * @author ELiaNaCc
 * @since 2022-03-09
 */
@Data
@ApiModel(value = "系统角色查询dto")
public class SysRoleSelectDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名
     */
    @ApiModelProperty(value = "角色名")
    private String roleName;

    /**
     * 状态- 0：禁用，1：启用
     */
    @ApiModelProperty(value = "状态- 0：禁用，1：启用", example = "1")
    private Integer roleStatus;

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
