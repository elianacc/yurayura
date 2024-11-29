package pers.elianacc.yurayura.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统数据字典 vo
 *
 * @author ELiaNaCc
 * @since 2024-11-04
 */
@Data
@ApiModel(value = "系统数据字典VO", description = "系统数据字典视图对象")
public class SysDictVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 字典编码
     */
    @ApiModelProperty(value = "字典编码")
    private String dictCode;

    /**
     * 字典名
     */
    @ApiModelProperty(value = "字典名")
    private String dictName;

    /**
     * 字典值
     */
    @ApiModelProperty(value = "字典值")
    private String dictVal;

    /**
     * 状态- 0：禁用，1：启用
     */
    @ApiModelProperty(value = "状态- 0：禁用，1：启用")
    private Integer dictStatus;

    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private Integer dictSeq;

}
