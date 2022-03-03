package org.elianacc.yurayura.entity.sys.dict;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统数据字典 entity
 *
 * @author ELiaNaCc
 * @since 2022-02-26
 */
@Data
@TableName("yurayura_sys_dict")
@ApiModel(value = "Dict对象", description = "系统数据字典")
public class SysDict implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 字典编码
     */
    @TableField("dict_code")
    @ApiModelProperty(value = "字典编码")
    private String dictCode;

    /**
     * 字典名
     */
    @TableField("dict_name")
    @ApiModelProperty(value = "字典名")
    private String dictName;

    /**
     * 字典值
     */
    @TableField("dict_val")
    @ApiModelProperty(value = "字典值")
    private String dictVal;

    /**
     * 状态- 0：禁用，1：启用
     */
    @TableField("dict_status")
    @ApiModelProperty(value = "状态- 0：禁用，1：启用")
    private Integer dictStatus;

    /**
     * 序号
     */
    @TableField("dict_seq")
    @ApiModelProperty(value = "序号")
    private Integer dictSeq;


}
