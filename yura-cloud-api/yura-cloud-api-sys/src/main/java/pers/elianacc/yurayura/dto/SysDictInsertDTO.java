package pers.elianacc.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 系统数据字典添加 dto
 *
 * @author ELiaNaCc
 * @since 2022-02-21
 */
@Data
@ApiModel(value = "系统数据字典添加dto")
public class SysDictInsertDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典编码
     */
    @NotBlank(message = "字典编码不能为空")
    @Size(max = 20, message = "字典编码不能超过20个字符")
    @Pattern(regexp = "^[a-z][A-Za-z]*$", message = "字典编码只能包含字母，以小写字母开头")
    @ApiModelProperty(value = "字典编码", required = true)
    private String dictCode;

    /**
     * 字典名
     */
    @NotBlank(message = "字典名不能为空")
    @Size(max = 20, message = "字典名不能超过20个字符")
    @ApiModelProperty(value = "字典名", required = true)
    private String dictName;

    /**
     * 字典值
     */
    @NotBlank(message = "字典值不能为空")
    @Size(max = 20, message = "字典值不能超过20个字符")
    @ApiModelProperty(value = "字典值", required = true)
    private String dictVal;

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
