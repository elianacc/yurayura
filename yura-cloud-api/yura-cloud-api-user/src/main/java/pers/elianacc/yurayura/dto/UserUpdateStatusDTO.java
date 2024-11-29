package pers.elianacc.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户状态修改 dto
 *
 * @author ELiaNaCc
 * @since 2022-02-17
 */
@Data
@ApiModel(value = "用户状态修改dto")
public class UserUpdateStatusDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Integer id;

    /**
     * 状态- 0：正常，-3：小黑屋3天，-7：小黑屋7天，-30：小黑屋30天，-365：小黑屋365天，-999：小黑屋永久
     */
    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态- 0：正常，-3：小黑屋3天，-7：小黑屋7天，-30：小黑屋30天，-365：小黑屋365天，-999：小黑屋永久", required = true, example = "0")
    private Integer userStatus;

}
