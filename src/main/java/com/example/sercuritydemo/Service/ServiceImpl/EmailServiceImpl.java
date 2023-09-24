package com.example.sercuritydemo.Service.ServiceImpl;

import com.example.sercuritydemo.Payload.ResponseDto.EmailDto;
import com.example.sercuritydemo.Service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
//    @Value("${spring.mail.username}")
//    private String senderEmail;

        private String senderName = "User Registration Portal Service";
        @Value("${spring.mail.username}")
        private String senderEmail;
//    @Override
//    public void sendEmailAlert(EmailDto emailDto) {
//        try {
//            var mailMessage = new SimpleMailMessage();
//            mailMessage.setFrom(senderEmail);
//            mailMessage.setTo(emailDto.getRecipient());
//            mailMessage.setText(emailDto.getMessageBody());
//            mailMessage.setSubject(emailDto.getSubject());
//            javaMailSender.send(mailMessage);
//            log.info("Mail sent successfully");
//        } catch (MailException e) {
//            throw new RuntimeException(e);
//        }
//    }
@Override
public void sendEmailAlert(EmailDto emailDto) {
    try {

        MimeMessage message = javaMailSender.createMimeMessage();
        var mailMessage = new MimeMessageHelper(message);
        mailMessage.setFrom(senderEmail,senderName);
        mailMessage.setTo(emailDto.getRecipient());
        mailMessage.setSubject(emailDto.getSubject());
        mailMessage.setText(emailDto.getMessageBody(), true);
        javaMailSender.send(message);

    } catch (MessagingException e) {
        throw new RuntimeException(e);
    } catch (UnsupportedEncodingException e) {
        throw new RuntimeException(e);
    }
}
}
