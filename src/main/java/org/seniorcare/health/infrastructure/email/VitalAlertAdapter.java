package org.seniorcare.health.infrastructure.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.seniorcare.health.application.ports.output.IVitalAlertPort;
import org.seniorcare.health.domain.vo.AbnormalVital;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class VitalAlertAdapter implements IVitalAlertPort {

    private static final Logger log = LoggerFactory.getLogger(VitalAlertAdapter.class);

    private final JavaMailSender mailSender;
    private final JdbcTemplate jdbcTemplate;
    private final String fromAddress;

    public VitalAlertAdapter(
            JavaMailSender mailSender,
            JdbcTemplate jdbcTemplate,
            @Value("${spring.mail.username:}") String fromAddress) {
        this.mailSender = mailSender;
        this.jdbcTemplate = jdbcTemplate;
        this.fromAddress = fromAddress;
    }

    @Override
    public void notifyAbnormalVitals(UUID residentId, List<AbnormalVital> abnormals) {
        if (fromAddress == null || fromAddress.isBlank()) {
            log.warn("MAIL_USERNAME not configured — skipping vital alert emails.");
            return;
        }

        List<Map<String, Object>> recipients = jdbcTemplate.queryForList(
                """
                SELECT u.email, u.name, r.name AS resident_name
                FROM resident_family_links rfl
                JOIN users u ON u.id = rfl.user_id
                JOIN residents r ON r.id = rfl.resident_id
                WHERE rfl.resident_id = ? AND rfl.deleted_at IS NULL AND u.is_active = true
                """,
                residentId
        );

        for (Map<String, Object> row : recipients) {
            String email = (String) row.get("email");
            String name = (String) row.get("name");
            String residentName = (String) row.get("resident_name");
            try {
                sendAlert(email, name, residentName, abnormals);
            } catch (Exception e) {
                log.error("Failed to send vital alert to {}: {}", email, e.getMessage());
            }
        }
    }

    private void sendAlert(String toEmail, String recipientName, String residentName,
                           List<AbnormalVital> abnormals) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromAddress);
        helper.setTo(toEmail);
        helper.setSubject("[ALERTA] Sinais vitais anormais — " + residentName + " | SeniorCare");
        helper.setText(buildBody(recipientName, residentName, abnormals), true);

        mailSender.send(message);
    }

    private String buildBody(String recipientName, String residentName, List<AbnormalVital> abnormals) {
        String rows = abnormals.stream()
                .map(v -> {
                    boolean isCritical = "CRITICAL".equals(v.severity());
                    String valueColor = isCritical ? "#dc2626" : "#d97706";
                    String badge = isCritical
                            ? "<span style=\"background:#fee2e2;color:#dc2626;padding:2px 6px;border-radius:4px;font-size:11px;font-weight:bold;\">CRÍTICO</span>"
                            : "<span style=\"background:#fef3c7;color:#d97706;padding:2px 6px;border-radius:4px;font-size:11px;font-weight:bold;\">ATENÇÃO</span>";
                    return """
                            <tr>
                                <td style="padding:8px 12px;font-weight:bold;">%s %s</td>
                                <td style="padding:8px 12px;font-size:16px;color:%s;font-weight:bold;">%s</td>
                                <td style="padding:8px 12px;color:#6b7280;">%s</td>
                            </tr>
                            """.formatted(v.name(), badge, valueColor, v.value(), v.reason());
                })
                .collect(Collectors.joining());

        return """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head><meta charset="UTF-8"></head>
                <body style="font-family:Arial,sans-serif;color:#333;max-width:600px;margin:0 auto;padding:24px;">
                    <div style="background:#fee2e2;border-left:4px solid #dc2626;padding:12px 16px;border-radius:4px;margin-bottom:20px;">
                        <strong style="color:#dc2626;">⚠ ALERTA DE SINAIS VITAIS</strong>
                    </div>
                    <p>Olá, <strong>%s</strong>.</p>
                    <p>Os seguintes sinais vitais de <strong>%s</strong> foram registrados fora dos valores de referência:</p>
                    <table style="border-collapse:collapse;width:100%%;margin:16px 0;border:1px solid #e5e7eb;border-radius:8px;overflow:hidden;">
                        <thead>
                            <tr style="background:#f8fafc;">
                                <th style="padding:8px 12px;text-align:left;font-size:12px;color:#6b7280;">Sinal</th>
                                <th style="padding:8px 12px;text-align:left;font-size:12px;color:#6b7280;">Valor</th>
                                <th style="padding:8px 12px;text-align:left;font-size:12px;color:#6b7280;">Referência</th>
                            </tr>
                        </thead>
                        <tbody>%s</tbody>
                    </table>
                    <p>A equipe da instituição já foi notificada. Em caso de dúvidas, entre em contato diretamente.</p>
                    <hr style="border:none;border-top:1px solid #eee;margin:24px 0;">
                    <p style="font-size:12px;color:#999;">Este é um e-mail automático do SeniorCare. Não responda.</p>
                </body>
                </html>
                """.formatted(recipientName, residentName, rows);
    }
}
