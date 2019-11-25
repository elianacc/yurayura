package org.cny.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * 邮件参数 dto
 *
 * @author CNY
 * @date 2019年11月25日 10:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "邮件参数dto")
public class MailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "邮件主题")
    private String title;

    @ApiModelProperty(value = "邮件接收邮箱")
    private String receiveEmail;

    @ApiModelProperty(value = "邮件内容-使用模板不需要")
    private String content;

    @ApiModelProperty(value = "邮件附件-邮件附件/模板数据")
    private Map<String, Object> annexOrData;

}
