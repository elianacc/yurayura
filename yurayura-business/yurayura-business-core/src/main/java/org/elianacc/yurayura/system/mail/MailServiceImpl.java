package org.elianacc.yurayura.system.mail;

import lombok.extern.slf4j.Slf4j;
import org.elianacc.yurayura.bo.MailBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * 邮件 service impl
 *
 * @author ELiaNaCc
 * @since 2019-11-25
 */
@Service
@Slf4j
public class MailServiceImpl implements MailService {

    // template模板引擎
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sendEmail;

    /**
     * 发送纯文本邮件
     *
     * @param bo
     */
    @Async
    @Override
    public void sendTextMail(MailBo bo) {
        // 建立邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sendEmail); // 发送人的邮箱
        message.setSubject(bo.getTitle()); // 主题
        message.setTo(bo.getReceiveEmail()); // 发给谁  对方邮箱
        message.setText(bo.getContent()); // 内容
        try {
            // 发送
            javaMailSender.send(message);
        } catch (MailException e) {
            log.error("纯文本邮件发送失败->message:{}", e.getMessage());
        }
    }

    /**
     * 发送富文本（附件，图片，html等）邮件
     *
     * @param bo
     * @param isShowHtml 是否解析html
     */
    @Async
    @Override
    public void sendHtmlMail(MailBo bo, boolean isShowHtml) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            // 是否发送的邮件是富文本（附件，图片，html等）
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(sendEmail);// 发送人的邮箱
            messageHelper.setTo(bo.getReceiveEmail());// 发给谁  对方邮箱
            messageHelper.setSubject(bo.getTitle());// 主题
            messageHelper.setText(bo.getContent(), isShowHtml);// false，显示原始html代码，无效果
            // 判断是否有附加图片等
            if (bo.getAnnexOrData() != null && bo.getAnnexOrData().size() > 0) {
                bo.getAnnexOrData().forEach((key, value) -> {
                    try {
                        File file = new File(String.valueOf(value));
                        if (file.exists()) {
                            messageHelper.addAttachment(key, new FileSystemResource(file));
                        }
                    } catch (MessagingException e) {
                        log.error("附件发送失败->message:{}", e.getMessage());
                    }
                });
            }
            // 发送
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("富文本邮件发送失败->message:{}", e.getMessage());
        }
    }

    /**
     * 发送模板邮件 使用thymeleaf模板
     * 若果使用freemarker模板
     * Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
     * configuration.setClassForTemplateLoading(this.getClass(), "/templates");
     * String emailContent = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("mail.ftl"), params);
     *
     * @param bo
     * @param templatePosition
     */
    @Async
    @Override
    public void sendTemplateMail(MailBo bo, String templatePosition) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(sendEmail);// 发送人的邮箱
            messageHelper.setTo(bo.getReceiveEmail());// 发给谁  对方邮箱
            messageHelper.setSubject(bo.getTitle()); // 主题
            // 使用模板thymeleaf
            // Context是导这个包import org.thymeleaf.context.Context;
            Context context = new Context();
            // 定义模板数据
            context.setVariables(bo.getAnnexOrData());
            // 获取thymeleaf的html模板
            String emailContent = templateEngine.process(templatePosition, context); // 指定模板路径
            messageHelper.setText(emailContent, true);
            // 发送邮件
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("模板邮件发送失败->message:{}", e.getMessage());
        }
    }

}
