package pers.elianacc.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 系统权限修改 dto
 *
 * @author ELiaNaCc
 * @since 2022-02-22
 */
@Data
@ApiModel(value = "系统权限修改dto")
public class SysPermissionUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Integer id;

    /**
     * 权限名称
     */
    @NotBlank(message = "权限名称不能为空")
    @Size(max = 20, message = "权限名称不能超过20个字符")
    @ApiModelProperty(value = "权限名称", required = true)
    private String permissionName;

    /**
     * 状态- 0：禁用，1：启用
     */
    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态- 0：禁用，1：启用", required = true, example = "1")
    private Integer permissionStatus;

    /**
     * 序号
     */
    @NotNull(message = "序号不能为空")
    @ApiModelProperty(value = "序号", required = true, example = "1")
    private Integer permissionSeq;

}
