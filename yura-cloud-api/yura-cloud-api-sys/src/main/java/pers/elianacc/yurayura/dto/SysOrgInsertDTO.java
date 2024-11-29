package pers.elianacc.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 系统组织添加 dto
 *
 * @author ELiaNaCc
 * @since 2024-05-22
 */
@Data
@ApiModel(value = "系统组织添加dto")
public class SysOrgInsertDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 组织名称
     */
    @NotBlank(message = "组织名称不能为空")
    @Size(max = 20, message = "组织名称不能超过20个字符")
    @ApiModelProperty(value = "组织名称", required = true)
    private String orgName;

}
