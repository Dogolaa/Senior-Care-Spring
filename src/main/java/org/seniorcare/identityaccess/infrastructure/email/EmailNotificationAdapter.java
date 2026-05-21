package org.seniorcare.identityaccess.infrastructure.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.seniorcare.identityaccess.application.ports.output.IEmailNotificationPort;
import org.seniorcare.shared.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationAdapter implements IEmailNotificationPort {

    private final JavaMailSender mailSender;
    private final String fromAddress;

    public EmailNotificationAdapter(
            JavaMailSender mailSender,
            @Value("${spring.mail.username:}") String fromAddress) {
        this.mailSender = mailSender;
        this.fromAddress = fromAddress;
    }

    @Override
    public void sendFamilyMemberWelcomeEmail(String toEmail, String recipientName, String temporaryPassword) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromAddress);
            helper.setTo(toEmail);
            helper.setSubject("Sua conta no SeniorCare foi criada");
            helper.setText(buildEmailBody(recipientName, toEmail, temporaryPassword), true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new BadRequestException("Falha ao enviar e-mail de boas-vindas para: " + toEmail);
        }
    }

    private String buildEmailBody(String name, String email, String temporaryPassword) {
        return """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head><meta charset="UTF-8"></head>
                <body style="font-family: Arial, sans-serif; color: #333; max-width: 600px; margin: 0 auto; padding: 24px;">
                    <h2 style="color: #2c7be5;">Bem-vindo(a) ao SeniorCare</h2>
                    <p>Olá, <strong>%s</strong>.</p>
                    <p>Uma conta foi criada para você na plataforma <strong>SeniorCare</strong> para que você acompanhe o cuidado do seu familiar.</p>
                    <p>Suas credenciais de acesso são:</p>
                    <table style="border-collapse: collapse; margin: 16px 0;">
                        <tr>
                            <td style="padding: 8px 16px 8px 0; font-weight: bold;">E-mail:</td>
                            <td style="padding: 8px 0;">%s</td>
                        </tr>
                        <tr>
                            <td style="padding: 8px 16px 8px 0; font-weight: bold;">Senha temporária:</td>
                            <td style="padding: 8px 0; font-family: monospace; font-size: 16px; background: #f0f0f0; padding: 4px 8px; border-radius: 4px;">%s</td>
                        </tr>
                    </table>
                    <p style="color: #e74c3c; font-weight: bold;">Por segurança, altere sua senha no primeiro acesso.</p>
                    <hr style="border: none; border-top: 1px solid #eee; margin: 24px 0;">
                    <p style="font-size: 12px; color: #999;">Este é um e-mail automático. Não responda a esta mensagem.</p>
                </body>
                </html>
                """.formatted(name, email, temporaryPassword);
    }
}
