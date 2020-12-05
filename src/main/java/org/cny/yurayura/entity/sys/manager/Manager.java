package org.cny.yurayura.entity.sys.manager;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统管理员 entity
 *
 * @author CNY
 * @since 2020-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
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
    @ApiModelProperty(value = "管理员名")
    private String managerName;

    /**
     * 管理员密码
     */
    @ApiModelProperty(value = "管理员密码")
    private String managerPassword;

    /**
     * 启用状态- 0：禁用，1：启用
     */
    @ApiModelProperty(value = "启用状态- 0：禁用，1：启用")
    private Integer managerStatus;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime managerCreateTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime managerUpdateTime;

    /**
     * 管理员权限
     */
    @ApiModelProperty(value = "管理员权限")
    private String managerPermission;


}
