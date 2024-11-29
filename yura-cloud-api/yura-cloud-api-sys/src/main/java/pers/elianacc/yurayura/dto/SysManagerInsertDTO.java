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
 * 系统管理员添加 dto
 *
 * @author ELiaNaCc
 * @since 2022-02-22
 */
@Data
@ApiModel(value = "系统管理员添加dto")
public class SysManagerInsertDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 管理员名
     */
    @NotBlank(message = "管理员名不能为空")
    @Size(max = 20, message = "管理员名不能超过20个字符")
    @ApiModelProperty(value = "管理员名", required = true)
    private String managerName;

    /**
     * 管理员密码
     */
    @NotBlank(message = "管理员密码不能为空")
    @ApiModelProperty(value = "管理员密码", required = true)
    private String managerPassword;

    /**
     * 状态- 0：禁用，1：启用
     */
    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态- 0：禁用，1：启用", required = true, example = "1")
    private Integer managerStatus;

    /**
     * 管理员组织
     */
    @NotNull(message = "管理员组织不能为空")
    @ApiModelProperty(value = "管理员组织")
    private Integer managerOrg;

    /**
     * 拥有角色id组
     */
    @Size(min = 1, message = "至少分配一个角色")
    @ApiModelProperty(value = "拥有角色id组")
    private List<Integer> roleIdArr;

}
