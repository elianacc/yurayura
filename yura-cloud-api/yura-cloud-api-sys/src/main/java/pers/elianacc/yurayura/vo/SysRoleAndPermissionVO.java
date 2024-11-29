package pers.elianacc.yurayura.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统角色及其权限 vo
 *
 * @author ELiaNaCc
 * @since 2023-02-17
 */
@Data
@ApiModel(value = "系统角色及其权限VO", description = "系统角色及其权限视图对象")
public class SysRoleAndPermissionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 角色名
     */
    @ApiModelProperty(value = "角色名")
    private String roleName;

    /**
     * 状态- 0：禁用，1：启用
     */
    @ApiModelProperty(value = "状态- 0：禁用，1：启用")
    private Integer roleStatus;

    /**
     * 角色组织
     */
    @ApiModelProperty(value = "角色组织")
    private Integer roleOrg;

    /**
     * 角色组织名
     */
    @ApiModelProperty(value = "角色组织名")
    private String roleOrgName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime roleCreateTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime roleUpdateTime;

    /**
     * 权限id组字符串
     */
    @ApiModelProperty(value = "权限id组字符串")
    private String permissionIdsStr;

    /**
     * 权限id组字符串
     */
    @ApiModelProperty(value = "权限名组字符串")
    private String permissionNamesStr;

}
