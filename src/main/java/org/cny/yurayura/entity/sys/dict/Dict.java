package org.cny.yurayura.entity.sys.dict;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
    private String dictCode;

    /**
     * 字典名
     */
    private String dictName;

    /**
     * 字典值
     */
    private String dictVal;

    /**
     * 启用状态- 0：禁用，1：启用
     */
    private Integer dictStatus;


}
