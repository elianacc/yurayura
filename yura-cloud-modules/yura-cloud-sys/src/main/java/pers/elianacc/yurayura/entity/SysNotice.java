package pers.elianacc.yurayura.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统通知 entity
 *
 * @author ELiaNaCc
 * @since 2024-12-13
 */
@Data
@TableName("yurayura_sys_notice")
@ApiModel(value = "SysNotice对象", description = "系统通知")
public class SysNotice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 通知发送者
     */
    @TableField("notice_sender")
    @ApiModelProperty(value = "通知发送者")
    private String noticeSender;

    /**
     * 通知内容
     */
    @TableField("notice_content")
    @ApiModelProperty(value = "通知内容")
    private String noticeContent;

    /**
     * 通知组织
     */
    @TableField("notice_org")
    @ApiModelProperty(value = "通知组织")
    private Integer noticeOrg;

    /**
     * 通知时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("notice_create_time")
    @ApiModelProperty(value = "通知时间")
    private LocalDateTime noticeCreateTime;


}
