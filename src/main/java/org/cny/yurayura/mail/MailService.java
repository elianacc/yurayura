package org.cny.yurayura.mail;

import org.cny.yurayura.dto.MailDTO;

/**
 * 邮件 service
 *
 * @author CNY
 * @since 2019-11-25
 */
public interface MailService {
    /**
     * 发送纯文本邮件
     *
     * @param dto
     */
    public void sendTextMail(MailDTO dto);

    /**
     * 发送富文本（附件，图片，html等）邮件
     *
     * @param dto
     * @param isShowHtml 是否解析html
     */
    public void sendHtmlMail(MailDTO dto, boolean isShowHtml);

    /**
     * 发送模板邮件 使用thymeleaf模板
     *
     * @param dto
     * @param templatePosition
     */
    public void sendTemplateMail(MailDTO dto, String templatePosition);
}
