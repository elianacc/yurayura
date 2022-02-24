package org.elianacc.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 系统管理员添加 dto
 *
 * @author ELiaNaCc
 * @since 2022-02-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "系统管理员添加dto")
public class ManagerInsertDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 管理员名
     */
    @ApiModelProperty(value = "管理员名", required = true)
    private String managerName;

    /**
     * 管理员密码
     */
    @ApiModelProperty(value = "管理员密码", required = true)
    private String managerPassword;

    /**
     * 状态- 0：禁用，1：启用
     */
    @ApiModelProperty(value = "状态- 0：禁用，1：启用", required = true, example = "1")
    private Integer managerStatus;

    /**
     * 拥有权限id组
     */
    @ApiModelProperty(value = "拥有权限id组")
    private List<Integer> permissionIdArr;

}
