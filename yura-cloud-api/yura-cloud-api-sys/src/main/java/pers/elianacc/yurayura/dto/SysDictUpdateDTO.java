package pers.elianacc.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 系统数据字典修改 dto
 *
 * @author ELiaNaCc
 * @since 2022-02-21
 */
@Data
@ApiModel(value = "系统数据字典修改dto")
public class SysDictUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Integer id;

    /**
     * 字典名
     */
    @NotBlank(message = "字典名不能为空")
    @Size(max = 20, message = "字典名不能超过20个字符")
    @ApiModelProperty(value = "字典名", required = true)
    private String dictName;

    /**
     * 状态- 0：禁用，1：启用
     */
    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态- 0：禁用，1：启用", required = true, example = "1")
    private Integer dictStatus;

    /**
     * 序号
     */
    @NotNull(message = "序号不能为空")
    @ApiModelProperty(value = "序号", required = true, example = "1")
    private Integer dictSeq;

}
