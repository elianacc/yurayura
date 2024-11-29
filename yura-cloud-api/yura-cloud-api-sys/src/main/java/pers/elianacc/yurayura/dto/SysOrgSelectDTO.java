package pers.elianacc.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 系统组织查询 dto
 *
 * @author ELiaNaCc
 * @since 2024-05-20
 */
@Data
@ApiModel(value = "系统组织查询dto")
public class SysOrgSelectDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    private String orgName;

    /**
     * 页码
     */
    @NotNull(message = "页码不能为空")
    @ApiModelProperty(value = "页码", required = true, example = "1")
    private Integer pageNum;

    /**
     * 页记录数
     */
    @NotNull(message = "页记录数不能为空")
    @ApiModelProperty(value = "页记录数", required = true, example = "10")
    private Integer pageSize;

}
