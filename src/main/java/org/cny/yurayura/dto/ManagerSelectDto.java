package org.cny.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 系统管理员 dto
 *
 * @author CNY
 * @since 2020-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "系统管理员dto")
public class ManagerSelectDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 管理员名
     */
    @ApiModelProperty(value = "管理员名")
    private String managerName;

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码", required = true)
    private Integer pageNum;

    /**
     * 页记录数
     */
    @ApiModelProperty(value = "页记录数")
    private Integer pageSize;

}
