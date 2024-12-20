package pers.elianacc.yurayura.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统通知添加 dto
 *
 * @author ELiaNaCc
 * @since 2024-12-20
 */
@Data
@ApiModel(value = "系统通知添加dto")
public class SysNoticeInsertDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 通知发送者
     */
    @NotBlank(message = "通知发送者不能为空")
    @ApiModelProperty(value = "通知发送者")
    private String noticeSender;

    /**
     * 通知内容
     */
    @NotBlank(message = "通知内容不能为空")
    @ApiModelProperty(value = "通知内容")
    private String noticeContent;

    /**
     * 通知组织
     */
    @NotNull(message = "通知组织不能为空")
    @ApiModelProperty(value = "通知组织")
    private Integer noticeOrg;

    /**
     * 通知时间
     */
    @NotNull(message = "通知时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "通知时间")
    private LocalDateTime noticeCreateTime;

}
