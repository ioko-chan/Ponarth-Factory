package com.solomennicova.AuthTemplate.Service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class EmailService {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${spring.mail.password}")
    private String password;

    private final SpringTemplateEngine templateEngine;

    public EmailService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public void sendAuth(String emailTo, String subject, String loginUser, String passwordUser) throws MessagingException {

        Properties properties = getProperties();

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        session.setDebug(true);

        Context context = new Context();
        Map<String, Object> contexTMap = new HashMap<>();
        contexTMap.put("login", loginUser);
        contexTMap.put("password", passwordUser);
        context.setVariables(contexTMap);
        String emailContent = templateEngine.process("authForm/index.html", context);

        MimeMessage message = new MimeMessage(session);
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        mimeMessageHelper.setFrom(new InternetAddress(from));
        mimeMessageHelper.setTo(new InternetAddress(emailTo));
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(emailContent, true);

        Transport.send(message);
    }

    public Properties getProperties(){
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.auth", "true");

        properties.put("mail.smtp.user", from);
        properties.put("mail.smtp.password", password);

        properties.setProperty("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.quitwait", "false");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.debug", "true");
        return properties;
    }
}
