package pers.elianacc.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 系统角色添加 dto
 *
 * @author ELiaNaCc
 * @since 2022-03-09
 */
@Data
@ApiModel(value = "系统角色添加dto")
public class SysRoleInsertDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名
     */
    @NotBlank(message = "角色名不能为空")
    @Size(max = 20, message = "角色名不能超过20个字符")
    @ApiModelProperty(value = "角色名", required = true)
    private String roleName;

    /**
     * 状态- 0：禁用，1：启用
     */
    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态- 0：禁用，1：启用", required = true, example = "1")
    private Integer roleStatus;

    /**
     * 角色组织
     */
    @NotNull(message = "角色组织不能为空")
    @ApiModelProperty(value = "角色组织")
    private Integer roleOrg;

    /**
     * 拥有权限id组
     */
    @Size(min = 1, message = "至少分配一个权限")
    @ApiModelProperty(value = "拥有权限id组")
    private List<Integer> permissionIdArr;

}
