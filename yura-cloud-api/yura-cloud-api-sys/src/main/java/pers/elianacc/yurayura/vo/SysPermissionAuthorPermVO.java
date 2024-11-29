package pers.elianacc.yurayura.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 权限授权-树形控件权限 vo
 *
 * @author ELiaNaCc
 * @since 2021-08-14
 */
@Data
@ApiModel(value = "权限授权-树形控件权限VO", description = "权限授权-树形控件权限视图对象")
public class SysPermissionAuthorPermVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限id
     */
    @ApiModelProperty("id")
    private Integer id;

    /**
     * 权限名
     */
    @ApiModelProperty("权限名")
    private String title;
}
