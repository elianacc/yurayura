package org.cny.yurayura.entity.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户 entity
 *
 * @author CNY
 * @since 2019-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("yurayura_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 昵称
     */
    private String userNickname;

    /**
     * 签名
     */
    private String userSignature;

    /**
     * 性别
     */
    private String userSex;

    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate userBirsday;

    /**
     * 省份
     */
    private String userProvince;

    /**
     * 城市
     */
    private String userCity;

    /**
     * 头像地址
     */
    private String userAvatarUrl;

    /**
     * 邮箱
     */
    private String userEmail;

    /**
     * 手机号
     */
    private String userPhoneNumber;

    /**
     * 状态- 0：正常，3：小黑屋3天，7：小黑屋7天，30：小黑屋30天，365：小黑屋365天，999：小黑屋永久
     */
    private Integer userStatus;

    /**
     * 注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime userRegTime;

    /**
     * 最近编辑时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime userCurrentEditTime;


}
