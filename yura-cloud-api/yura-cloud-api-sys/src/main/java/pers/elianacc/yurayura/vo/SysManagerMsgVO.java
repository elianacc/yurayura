package pers.elianacc.yurayura.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统管理员登入信息 vo
 *
 * @author ELiaNaCc
 * @since 2023-02-17
 */
@Data
@ApiModel(value = "系统管理员登入信息VO", description = "系统管理员登入信息视图对象")
public class SysManagerMsgVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 管理员名
     */
    @ApiModelProperty(value = "管理员名")
    private String managerName;

    /**
     * 管理员组织
     */
    @ApiModelProperty(value = "管理员组织")
    private Integer managerOrg;

    /**
     * 管理员所有权限
     */
    @ApiModelProperty(value = "管理员拥有权限组")
    private String managerPermission;

}
