package org.cny.yurayura.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author CNY
 * @since 2019-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("yurayura_user")
@ApiModel(value="User对象", description="用户")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String userPassword;

    @ApiModelProperty(value = "昵称")
    private String userNickname;

    @ApiModelProperty(value = "签名")
    private String userSignature;

    @ApiModelProperty(value = "性别")
    private String userSex;

    @ApiModelProperty(value = "生日")
    private String userBirsday;

    @ApiModelProperty(value = "省份")
    private String userProvince;

    @ApiModelProperty(value = "城市")
    private String userCity;

    @ApiModelProperty(value = "头像地址")
    private String userAvatarUrl;

    @ApiModelProperty(value = "邮箱")
    private String userEmail;

    @ApiModelProperty(value = "手机号")
    private String userPhoneNumber;

    @ApiModelProperty(value = "状态  0：正常  其他数字：小黑屋天数")
    private Integer userStatus;

    @ApiModelProperty(value = "注册时间")
    private String userRegTime;


}
