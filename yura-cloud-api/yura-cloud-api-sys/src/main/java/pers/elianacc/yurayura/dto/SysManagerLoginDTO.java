package pers.elianacc.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 系统管理员登入 dto
 *
 * @author ELiaNaCc
 * @since 2019-11-21
 */
@Data
@ApiModel(value = "系统管理员登入dto")
public class SysManagerLoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 管理员名
     */
    @NotBlank(message = "管理员名不能为空")
    @ApiModelProperty(value = "管理员名", required = true)
    private String managerName;

    /**
     * 管理员密码
     */
    @NotBlank(message = "管理员密码不能为空")
    @ApiModelProperty(value = "管理员密码", required = true)
    private String managerPassword;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码", required = true)
    private String verifyCode;

}
