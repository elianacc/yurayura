package org.cny.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 管理员登入 dto
 *
 * @author CNY
 * @date 2019年11月21日 17:47
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "管理员登入dto")
public class MangerLoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "管理员名")
    private String managerName;

    @ApiModelProperty(value = "管理员密码")
    private String managerPassword;

    @ApiModelProperty(value = "验证码")
    private String verifyCode;

}
