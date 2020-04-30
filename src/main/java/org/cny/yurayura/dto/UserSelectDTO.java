package org.cny.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户查询dto
 *
 * @author CNY
 * @since 2020-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "用户查询dto")
public class UserSelectDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String userSex;

    /**
     * 状态- 0：正常，3：小黑屋3天，7：小黑屋7天，30：小黑屋30天，365：小黑屋365天，999：小黑屋永久
     */
    @ApiModelProperty(value = "状态")
    private Integer userStatus;

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码", required = true)
    private Integer pageNum;

    /**
     * 页记录数
     */
    @ApiModelProperty(value = "页记录数")
    private Integer pageSize;

}
