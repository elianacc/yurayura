package pers.elianacc.yurayura.bo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 用户导出 bo
 *
 * @author ELiaNaCc
 * @since 2024-11-07
 */
@Data
public class UserExportBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 头像
     */
    @Excel(name = "头像", type = 2, width = 13, height = 30, orderNum = "1")
    private String userAvatar;

    /**
     * 用户名
     */
    @Excel(name = "用户名", width = 20, orderNum = "2")
    private String userName;

    /**
     * 昵称
     */
    @Excel(name = "昵称", width = 20, orderNum = "3")
    private String userNickname;

    /**
     * 性别- 1：男，2：女
     */
    @Excel(name = "性别", replace = { "男_1", "女_2" }, width = 10, orderNum = "4")
    private Integer userSex;

    /**
     * 生日
     */
    @Excel(name = "生日", width = 20, orderNum = "5")
    private LocalDate userBirthday;

    /**
     * 所在省市
     */
    @Excel(name = "所在省市", width = 30, orderNum = "6")
    private String belongProvCity;

    /**
     * 邮箱
     */
    @Excel(name = "邮箱", width = 20, orderNum = "7")
    private String userEmail;

    /**
     * 手机号
     */
    @Excel(name = "手机号", width = 20, orderNum = "8")
    private String userPhoneNumber;

}
