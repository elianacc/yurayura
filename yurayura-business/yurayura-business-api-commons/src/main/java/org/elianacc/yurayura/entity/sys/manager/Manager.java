package org.elianacc.yurayura.entity.sys.manager;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 系统管理员 entity
 *
 * @author ELiaNaCc
 * @since 2022-02-26
 */
@Data
@TableName("yurayura_sys_manager")
@ApiModel(value = "Manager对象", description = "系统管理员")
public class Manager implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 管理员名
     */
    @TableField("manager_name")
    @ApiModelProperty(value = "管理员名")
    private String managerName;

    /**
     * 管理员密码
     */
    @TableField("manager_password")
    @ApiModelProperty(value = "管理员密码")
    private String managerPassword;

    /**
     * 状态- 0：禁用，1：启用
     */
    @TableField("manager_status")
    @ApiModelProperty(value = "状态- 0：禁用，1：启用")
    private Integer managerStatus;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("manager_create_time")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime managerCreateTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("manager_update_time")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime managerUpdateTime;


}
