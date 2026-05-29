package org.seniorcare.health.domain.vo;

import java.util.ArrayList;
import java.util.List;

public final class VitalThresholds {

    private VitalThresholds() {}

    public static List<AbnormalVital> evaluate(
            Integer heartRate,
            Float saturation,
            String bloodPressure,
            Float temperature) {

        List<AbnormalVital> abnormals = new ArrayList<>();

        if (heartRate != null) {
            if (heartRate < 40) {
                abnormals.add(new AbnormalVital("Frequência Cardíaca", heartRate + " bpm", "Abaixo de 40 bpm (bradicardia grave)"));
            } else if (heartRate > 120) {
                abnormals.add(new AbnormalVital("Frequência Cardíaca", heartRate + " bpm", "Acima de 120 bpm (taquicardia)"));
            }
        }

        if (saturation != null) {
            if (saturation < 90f) {
                abnormals.add(new AbnormalVital("Saturação (SpO₂)", saturation + "%", "Abaixo de 90% (hipoxemia)"));
            } else if (saturation < 95f) {
                abnormals.add(new AbnormalVital("Saturação (SpO₂)", saturation + "%", "Abaixo de 95% (atenção)"));
            }
        }

        if (temperature != null) {
            if (temperature < 35f) {
                abnormals.add(new AbnormalVital("Temperatura", temperature + "°C", "Abaixo de 35°C (hipotermia)"));
            } else if (temperature > 38.5f) {
                abnormals.add(new AbnormalVital("Temperatura", temperature + "°C", "Acima de 38.5°C (febre)"));
            }
        }

        if (bloodPressure != null && bloodPressure.contains("/")) {
            try {
                String[] parts = bloodPressure.split("/");
                int systolic = Integer.parseInt(parts[0].trim());
                int diastolic = Integer.parseInt(parts[1].trim());

                if (systolic > 160) {
                    abnormals.add(new AbnormalVital("Pressão Arterial", bloodPressure + " mmHg", "Sistólica acima de 160 mmHg (hipertensão grave)"));
                } else if (systolic < 80) {
                    abnormals.add(new AbnormalVital("Pressão Arterial", bloodPressure + " mmHg", "Sistólica abaixo de 80 mmHg (hipotensão)"));
                } else if (diastolic > 100) {
                    abnormals.add(new AbnormalVital("Pressão Arterial", bloodPressure + " mmHg", "Diastólica acima de 100 mmHg (hipertensão)"));
                }
            } catch (NumberFormatException ignored) {}
        }

        return abnormals;
    }
}
