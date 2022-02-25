package org.elianacc.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统数据字典修改 dto
 *
 * @author ELiaNaCc
 * @since 2022-02-21
 */
@Data
@ApiModel(value = "系统数据字典修改dto")
public class DictUpdateDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Integer id;

    /**
     * 字典名
     */
    @ApiModelProperty(value = "字典名", required = true)
    private String dictName;

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
