package pers.elianacc.yurayura.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统组织 entity
 *
 * @author ELiaNaCc
 * @since 2024-05-14
 */
@Data
@TableName("yurayura_sys_org")
@ApiModel(value = "SysOrg对象", description = "系统组织")
public class SysOrg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 组织名称
     */
    @TableField("org_name")
    @ApiModelProperty(value = "组织名称")
    private String orgName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("org_create_time")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime orgCreateTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("org_update_time")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime orgUpdateTime;


}
