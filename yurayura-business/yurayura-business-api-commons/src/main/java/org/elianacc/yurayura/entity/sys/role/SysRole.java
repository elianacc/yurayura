package org.elianacc.yurayura.entity.sys.role;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 系统角色 entity
 *
 * @author ELiaNaCc
 * @since 2022-03-07
 */
@Data
@TableName("yurayura_sys_role")
@ApiModel(value = "SysRole对象", description = "系统角色")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 角色名
     */
    @TableField("role_name")
    @ApiModelProperty(value = "角色名")
    private String roleName;

    /**
     * 状态- 0：禁用，1：启用
     */
    @TableField("role_status")
    @ApiModelProperty(value = "状态- 0：禁用，1：启用")
    private Integer roleStatus;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("role_create_time")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime roleCreateTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("role_update_time")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime roleUpdateTime;


}
