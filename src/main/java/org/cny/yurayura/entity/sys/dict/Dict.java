package org.cny.yurayura.entity.sys.dict;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 系统数据字典 entity
 *
 * @author CNY
 * @since 2020-03-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("yurayura_sys_dict")
public class Dict implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
     * 启用状态- 0：禁用，1：启用
     */
    @ApiModelProperty(value = "启用状态- 0：禁用，1：启用", required = true)
    private Integer dictStatus;


}
