package com.iotech.qualitrack.platform.iam.infrastructure.notifications.smtp;

import com.iotech.qualitrack.platform.iam.application.internal.outboundservices.credentials.CredentialsNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Sends the staff credentials by e-mail through the SMTP server configured with the {@code spring.mail.*}
 * properties (for example MAIL_HOST, MAIL_USERNAME and MAIL_PASSWORD). Without an SMTP server nothing is sent
 * and the quality manager receives the temporary password once to hand it over.
 */
@Slf4j
@Component
public class SmtpCredentialsNotifier implements CredentialsNotifier {
    private final ObjectProvider<JavaMailSender> mailSender;
    private final String from;
    private final String signInUrl;

    public SmtpCredentialsNotifier(ObjectProvider<JavaMailSender> mailSender,
                                   @Value("${qualitrack.mail.from:}") String from,
                                   @Value("${qualitrack.web.sign-in-url:http://localhost:4200/iam/sign-in}") String signInUrl) {
        this.mailSender = mailSender;
        this.from = from;
        this.signInUrl = signInUrl;
    }

    @Override
    public boolean send(StaffCredentials credentials) {
        var sender = mailSender.getIfAvailable();
        if (sender == null) {
            log.info("SMTP is not configured; the credentials of {} are returned to the quality manager", credentials.username());
            return false;
        }
        var message = new SimpleMailMessage();
        if (!from.isBlank()) message.setFrom(from);
        message.setTo(credentials.email());
        message.setSubject("QualiTrack - Credenciales de acceso / Access credentials");
        message.setText("""
                Hola %1$s,

                Tu responsable de calidad te registró en QualiTrack.
                Usuario: %2$s
                Contraseña temporal: %3$s
                Ingresa en %4$s; al iniciar sesión deberás definir una nueva contraseña.

                Hello %1$s,

                Your quality manager registered you in QualiTrack.
                Username: %2$s
                Temporary password: %3$s
                Sign in at %4$s; you will be asked to choose a new password.
                """.formatted(credentials.fullName(), credentials.username(), credentials.temporaryPassword(), signInUrl));
        try {
            sender.send(message);
            return true;
        } catch (MailException exception) {
            log.warn("The credentials of {} could not be e-mailed: {}", credentials.username(), exception.getMessage());
            return false;
        }
    }
}
