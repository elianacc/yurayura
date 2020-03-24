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
 * @since 2020-03-24
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
    private String code;

    /**
     * 字典名
     */
    private String name;

    /**
     * 字典值
     */
    private String val;

    /**
     * 启用状态- 0：禁用，1：启用
     */
    private Integer status;


}
