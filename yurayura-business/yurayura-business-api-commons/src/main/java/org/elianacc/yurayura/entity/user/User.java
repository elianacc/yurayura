package org.elianacc.yurayura.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户 entity
 *
 * @author ELiaNaCc
 * @since 2022-02-26
 */
@Data
@TableName("yurayura_user")
@ApiModel(value = "User对象", description = "用户")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 用户名
     */
    @TableField("user_name")
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 密码
     */
    @TableField("user_password")
    @ApiModelProperty(value = "密码")
    private String userPassword;

    /**
     * 昵称
     */
    @TableField("user_nickname")
    @ApiModelProperty(value = "昵称")
    private String userNickname;

    /**
     * 签名
     */
    @TableField("user_signature")
    @ApiModelProperty(value = "签名")
    private String userSignature;

    /**
     * 性别- 1：男，2：女
     */
    @TableField("user_sex")
    @ApiModelProperty(value = "性别- 1：男，2：女")
    private Integer userSex;

    /**
     * 生日
     */
    @TableField("user_birthday")
    @ApiModelProperty(value = "生日")
    private LocalDate userBirthday;

    /**
     * 省份
     */
    @TableField("user_province")
    @ApiModelProperty(value = "省份")
    private String userProvince;

    /**
     * 城市
     */
    @TableField("user_city")
    @ApiModelProperty(value = "城市")
    private String userCity;

    /**
     * 头像地址
     */
    @TableField("user_avatar_url")
    @ApiModelProperty(value = "头像地址")
    private String userAvatarUrl;

    /**
     * 邮箱
     */
    @TableField("user_email")
    @ApiModelProperty(value = "邮箱")
    private String userEmail;

    /**
     * 手机号
     */
    @TableField("user_phone_number")
    @ApiModelProperty(value = "手机号")
    private String userPhoneNumber;

    /**
     * 状态- 0：正常，-3：小黑屋3天，-7：小黑屋7天，-30：小黑屋30天，-365：小黑屋365天，-999：小黑屋永久
     */
    @TableField("user_status")
    @ApiModelProperty(value = "状态- 0：正常，-3：小黑屋3天，-7：小黑屋7天，-30：小黑屋30天，-365：小黑屋365天，-999：小黑屋永久")
    private Integer userStatus;

    /**
     * 注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("user_reg_time")
    @ApiModelProperty(value = "注册时间")
    private LocalDateTime userRegTime;

    /**
     * 最近编辑时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("user_current_edit_time")
    @ApiModelProperty(value = "最近编辑时间")
    private LocalDateTime userCurrentEditTime;


}
