package com.example.dtrecords.service;

import com.example.dtrecords.model.Vinyl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.Map;

@Service
public class MailService {

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    TemplateEngine templateEngine;

    public void sendEmail(String subject, String message, String recipientEmail, String customerName, String customerPhone, String customerAddress, float totalPrice, Map<Vinyl, Integer> selectedVinyl) throws MessagingException {
        Locale locale = LocaleContextHolder.getLocale();

        Context ctx = new Context(locale);
        ctx.setVariable("message", message);
        ctx.setVariable("customerName", customerName);
        ctx.setVariable("customerPhone", customerPhone);
        ctx.setVariable("customerAddress", customerAddress);
        ctx.setVariable("customerEmail", recipientEmail);
        ctx.setVariable("selectedVinyl", selectedVinyl);
        ctx.setVariable("totalPrice", totalPrice);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setTo(recipientEmail);

        String htmlContent = "";
        htmlContent = templateEngine.process("mail/email_en.html", ctx);
        mimeMessageHelper.setText(htmlContent, true);

        mailSender.send(mimeMessage);
    }
}
