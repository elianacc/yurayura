package org.elianacc.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 系统数据字典添加 dto
 *
 * @author ELiaNaCc
 * @since 2022-02-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "系统数据字典添加dto")
public class DictInsertDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典编码
     */
    @ApiModelProperty(value = "字典编码", required = true)
    private String dictCode;

    /**
     * 字典名
     */
    @ApiModelProperty(value = "字典名", required = true)
    private String dictName;

    /**
     * 字典值
     */
    @ApiModelProperty(value = "字典值", required = true)
    private String dictVal;

    /**
     * 状态- 0：禁用，1：启用
     */
    @ApiModelProperty(value = "状态- 0：禁用，1：启用", required = true, example = "1")
    private Integer dictStatus;

    /**
     * 序号
     */
    @ApiModelProperty(value = "序号", required = true, example = "1")
    private Integer dictSeq;

}
