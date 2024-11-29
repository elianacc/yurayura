package pers.elianacc.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 系统管理员修改 dto
 *
 * @author ELiaNaCc
 * @since 2022-02-22
 */
@Data
@ApiModel(value = "系统管理员修改dto")
public class SysManagerUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Integer id;

    /**
     * 管理员密码
     */
    @ApiModelProperty(value = "管理员密码")
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
