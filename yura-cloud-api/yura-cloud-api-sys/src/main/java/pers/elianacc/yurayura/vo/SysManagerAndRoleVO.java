package pers.elianacc.yurayura.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统管理员及其角色 vo
 *
 * @author ELiaNaCc
 * @since 2023-02-17
 */
@Data
@ApiModel(value = "系统管理员及其角色VO", description = "系统管理员及其角色视图对象")
public class SysManagerAndRoleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 管理员名
     */
    @ApiModelProperty(value = "管理员名")
    private String managerName;

    /**
     * 状态- 0：禁用，1：启用
     */
    @ApiModelProperty(value = "状态- 0：禁用，1：启用")
    private Integer managerStatus;

    /**
     * 管理员组织
     */
    @ApiModelProperty(value = "管理员组织")
    private Integer managerOrg;

    /**
     * 管理员组织名
     */
    @ApiModelProperty(value = "管理员组织名")
    private String managerOrgName;

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
     * 角色id组字符串
     */
    @ApiModelProperty(value = "角色id组字符串")
    private String roleIdsStr;

    /**
     * 角色id组字符串
     */
    @ApiModelProperty(value = "角色名组字符串")
    private String roleNamesStr;

}
