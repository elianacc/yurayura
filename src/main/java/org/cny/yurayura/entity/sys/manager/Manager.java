package org.cny.yurayura.entity.sys.manager;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 管理员
 * </p>
 *
 * @author CNY
 * @since 2019-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("yurayura_sys_manager")
@ApiModel(value="Manager对象", description="管理员")
public class Manager implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "管理员名")
    private String managerName;

    @ApiModelProperty(value = "管理员密码")
    private String managerPassword;

    @ApiModelProperty(value = "启用状态- 0：禁用，1：启用")
    private Integer managerStatus;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime managerCreateTime;


}
