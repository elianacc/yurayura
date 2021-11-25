package org.elianacc.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 系统管理员登入 dto
 *
 * @author ELiaNaCc
 * @since 2019-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "系统管理员登入dto")
public class ManagerLoginDto implements Serializable {

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
     * 验证码
     */
    @ApiModelProperty(value = "验证码", required = true)
    private String verifyCode;

}
