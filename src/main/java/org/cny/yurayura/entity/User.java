package org.cny.yurayura.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
@ApiModel(value = "User对象", description = "用户")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

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
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate userBirsday;

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

    @ApiModelProperty(value = "状态- 0：正常，3：小黑屋3天，7：小黑屋7天，30：小黑屋30天，365：小黑屋365天，999：小黑屋永久")
    private Integer userStatus;

    @ApiModelProperty(value = "注册时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime userRegTime;


}
