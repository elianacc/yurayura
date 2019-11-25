package org.cny.yurayura.mail;

import org.cny.yurayura.dto.MailDTO;

/**
 * 邮件 service
 *
 * @author CNY
 * @date 2019年11月25日 11:12
 */
public interface MailService {
    public void sendTextMail(MailDTO mailDTO);

    public void sendHtmlMail(MailDTO mailDTO, boolean isShowHtml);

    public void sendTemplateMail(MailDTO mailDTO, String templatePosition);
}
