package org.seniorcare.incidents.infrastructure.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.seniorcare.incidents.application.ports.output.IIncidentAlertPort;
import org.seniorcare.incidents.domain.aggregates.Incident;
import org.seniorcare.incidents.domain.vo.IncidentSeverity;
import org.seniorcare.incidents.domain.vo.IncidentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Component
public class IncidentAlertAdapter implements IIncidentAlertPort {

    private static final Logger log = LoggerFactory.getLogger(IncidentAlertAdapter.class);

    private final JavaMailSender mailSender;
    private final JdbcTemplate jdbcTemplate;
    private final String fromAddress;

    public IncidentAlertAdapter(
            JavaMailSender mailSender,
            JdbcTemplate jdbcTemplate,
            @Value("${spring.mail.username:}") String fromAddress) {
        this.mailSender = mailSender;
        this.jdbcTemplate = jdbcTemplate;
        this.fromAddress = fromAddress;
    }

    @Override
    public void notifyFamilyMembers(Incident incident) {
        if (fromAddress == null || fromAddress.isBlank()) {
            log.warn("MAIL_USERNAME not configured — skipping incident alert emails.");
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
                incident.getResidentId()
        );

        for (Map<String, Object> row : recipients) {
            String email = (String) row.get("email");
            String name = (String) row.get("name");
            String residentName = (String) row.get("resident_name");
            try {
                sendAlert(email, name, residentName, incident);
            } catch (Exception e) {
                log.error("Failed to send incident alert to {}: {}", email, e.getMessage());
            }
        }
    }

    private void sendAlert(String toEmail, String recipientName, String residentName, Incident incident)
            throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromAddress);
        helper.setTo(toEmail);
        helper.setSubject(buildSubject(incident.getSeverity(), residentName));
        helper.setText(buildBody(recipientName, residentName, incident), true);

        mailSender.send(message);
    }

    private String buildSubject(IncidentSeverity severity, String residentName) {
        String prefix = severity == IncidentSeverity.CRITICAL || severity == IncidentSeverity.HIGH
                ? "[URGENTE] "
                : "";
        return prefix + "Incidente registrado com " + residentName + " — SeniorCare";
    }

    private String buildBody(String recipientName, String residentName, Incident incident) {
        String severityLabel = switch (incident.getSeverity()) {
            case LOW -> "Baixa";
            case MEDIUM -> "Média";
            case HIGH -> "<strong style=\"color:#e67e22\">Alta</strong>";
            case CRITICAL -> "<strong style=\"color:#e74c3c\">Crítica</strong>";
        };

        String typeLabel = switch (incident.getIncidentType()) {
            case FALL -> "Queda";
            case AGITATION -> "Agitação";
            case MEDICATION_REFUSAL -> "Recusa de Medicamento";
            case BEHAVIORAL_CHANGE -> "Mudança Comportamental";
            case ACCIDENT -> "Acidente";
            case OTHER -> "Outro";
        };

        String occurredAt = incident.getOccurredAt()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm"));

        String actionBlock = incident.getActionTaken() != null && !incident.getActionTaken().isBlank()
                ? """
                  <p><strong>Ação tomada pela equipe:</strong></p>
                  <p style="background:#f0f9f0;padding:12px;border-left:4px solid #27ae60;border-radius:4px;">%s</p>
                  """.formatted(incident.getActionTaken())
                : "<p>A equipe está acompanhando a situação.</p>";

        return """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head><meta charset="UTF-8"></head>
                <body style="font-family:Arial,sans-serif;color:#333;max-width:600px;margin:0 auto;padding:24px;">
                    <h2 style="color:#2c7be5;">SeniorCare — Notificação de Incidente</h2>
                    <p>Olá, <strong>%s</strong>.</p>
                    <p>Informamos que um incidente foi registrado com o seu familiar <strong>%s</strong>:</p>
                    <table style="border-collapse:collapse;width:100%%;margin:16px 0;background:#f8f9fa;border-radius:8px;">
                        <tr><td style="padding:10px 16px;font-weight:bold;width:40%%;">Tipo</td>
                            <td style="padding:10px 16px;">%s</td></tr>
                        <tr style="background:#fff;"><td style="padding:10px 16px;font-weight:bold;">Severidade</td>
                            <td style="padding:10px 16px;">%s</td></tr>
                        <tr><td style="padding:10px 16px;font-weight:bold;">Data e hora</td>
                            <td style="padding:10px 16px;">%s</td></tr>
                        %s
                    </table>
                    <p><strong>Descrição:</strong></p>
                    <p style="background:#fff3cd;padding:12px;border-left:4px solid #ffc107;border-radius:4px;">%s</p>
                    %s
                    <p>Em caso de dúvidas, entre em contato com a equipe da instituição.</p>
                    <hr style="border:none;border-top:1px solid #eee;margin:24px 0;">
                    <p style="font-size:12px;color:#999;">Este é um e-mail automático enviado pelo SeniorCare. Não responda.</p>
                </body>
                </html>
                """.formatted(
                recipientName,
                residentName,
                typeLabel,
                severityLabel,
                occurredAt,
                incident.getRoom() != null
                        ? "<tr style=\"background:#fff;\"><td style=\"padding:10px 16px;font-weight:bold;\">Local</td><td style=\"padding:10px 16px;\">%s</td></tr>".formatted(incident.getRoom())
                        : "",
                incident.getDescription(),
                actionBlock
        );
    }
}
