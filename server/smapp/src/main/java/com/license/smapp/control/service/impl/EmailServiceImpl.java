package com.license.smapp.control.service.impl;

import com.license.smapp.common.Email;
import com.license.smapp.entity.model.User;
import com.license.smapp.control.service.EmailService;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    @Qualifier("freeMarkerConfigBean")
    private Configuration freemarkerConfig;

    @Override
    public Email getEmail(User user, String subject) {
        Email email = new Email();
        email.setFrom("fiilicense@gmail.com");
        email.setTo(user.getEmail());
        email.setSubject(subject);

        Map<String, String> content = new HashMap<>();
        content.put("name", user.getName());
        content.put("password", user.getPassword());
        email.setContent(content);

        return email;
    }

    @Override
    public void sendEmail(Email email) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            Template t = freemarkerConfig.getTemplate("account_created.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, email.getContent());

            mimeMessageHelper.setFrom(email.getFrom());
            mimeMessageHelper.setTo(email.getTo());
            mimeMessageHelper.setSubject(email.getSubject());
            mimeMessageHelper.setText(html, true);
            javaMailSender.send(mimeMessage);
        } catch (TemplateNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
