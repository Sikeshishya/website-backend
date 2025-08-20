package com.filcon.electronics.service;

import com.filcon.electronics.entity.ContactInquiry;
import com.filcon.electronics.util.EmailTemplateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${filcon.email.from}")
    private String fromEmail;

    @Value("${filcon.email.to}")
    private String[] notificationEmails;

    @Async
    public void sendConfirmationEmail(ContactInquiry inquiry) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail, "Filcon Electronics");
            helper.setTo(inquiry.getEmail());
            helper.setSubject("Thank you for contacting Filcon Electronics");

            Context context = new Context();
            context.setVariable("inquiry", inquiry);

            String htmlContent = templateEngine.process("contact-confirmation", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Confirmation email sent to: {}", inquiry.getEmail());

        } catch (Exception e) {
            log.error("Failed to send confirmation email to: {}", inquiry.getEmail(), e);
        }
    }

    @Async
    public void sendNotificationEmail(ContactInquiry inquiry) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail, "Filcon Electronics Website");
            helper.setTo(notificationEmails);
            helper.setSubject("New Contact Inquiry - " + inquiry.getFirstName() + " " + inquiry.getLastName());

            Context context = new Context();
            context.setVariable("inquiry", inquiry);

            String htmlContent = templateEngine.process("contact-notification", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Notification email sent for inquiry ID: {}", inquiry.getId());

        } catch (Exception e) {
            log.error("Failed to send notification email for inquiry ID: {}", inquiry.getId(), e);
        }
    }
}