package org.elianacc.yurayura.system.mail;

import org.elianacc.yurayura.bo.MailBo;

/**
 * 邮件 service
 *
 * @author ELiaNaCc
 * @since 2019-11-25
 */
public interface MailService {
    /**
     * 发送纯文本邮件
     *
     * @param bo
     */
    public void sendTextMail(MailBo bo);

    /**
     * 发送富文本（附件，图片，html等）邮件
     *
     * @param bo
     * @param isShowHtml 是否解析html
     */
    public void sendHtmlMail(MailBo bo, boolean isShowHtml);

    /**
     * 发送模板邮件 使用thymeleaf模板
     *
     * @param bo
     * @param templatePosition
     */
    public void sendTemplateMail(MailBo bo, String templatePosition);
}
