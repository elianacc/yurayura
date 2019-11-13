package org.cny.yurayura.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author CNY
 * @since 2019-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("yurayura_user")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 用户id
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
    private String userBirsday;

    /**
     * 省份
     */
    private String userProvince;

    /**
     * 城市
     */
    private String userCity;

    /**
     * 头像
     */
    private String userImage;

    /**
     * 邮箱
     */
    private String userEmail;

    /**
     * 手机号
     */
    private String userPhoneNum;

    /**
     * 状态  0：正常  其他数字：小黑屋天数
     */
    private Integer userStatus;

    /**
     * 注册时间
     */
    private String userRegTime;


}
