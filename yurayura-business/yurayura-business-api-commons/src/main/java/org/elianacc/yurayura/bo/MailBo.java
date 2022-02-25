package org.elianacc.yurayura.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 邮件参数 bo
 *
 * @author ELiaNaCc
 * @since 2019-11-25
 */
@Data
public class MailBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 邮件主题
     */
    private String title;

    /**
     * 邮件接收邮箱
     */
    private String receiveEmail;

    /**
     * 邮件内容-使用模板不需要
     */
    private String content;

    /**
     * 邮件附件-邮件附件/模板数据
     */
    private Map<String, Object> annexOrData;

}
