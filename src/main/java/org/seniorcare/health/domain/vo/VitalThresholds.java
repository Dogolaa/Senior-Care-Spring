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
                abnormals.add(new AbnormalVital("Frequência Cardíaca", heartRate + " bpm", "Abaixo de 40 bpm (bradicardia grave)", "CRITICAL"));
            } else if (heartRate > 120) {
                abnormals.add(new AbnormalVital("Frequência Cardíaca", heartRate + " bpm", "Acima de 120 bpm (taquicardia)", "CRITICAL"));
            }
        }

        if (saturation != null) {
            if (saturation < 90f) {
                abnormals.add(new AbnormalVital("Saturação (SpO₂)", saturation + "%", "Abaixo de 90% (hipoxemia)", "CRITICAL"));
            } else if (saturation < 95f) {
                abnormals.add(new AbnormalVital("Saturação (SpO₂)", saturation + "%", "Abaixo de 95% (atenção)", "WARNING"));
            }
        }

        if (temperature != null) {
            if (temperature < 35f) {
                abnormals.add(new AbnormalVital("Temperatura", temperature + "°C", "Abaixo de 35°C (hipotermia)", "CRITICAL"));
            } else if (temperature > 38.5f) {
                abnormals.add(new AbnormalVital("Temperatura", temperature + "°C", "Acima de 38.5°C (febre)", "CRITICAL"));
            }
        }

        if (bloodPressure != null && bloodPressure.contains("/")) {
            try {
                String[] parts = bloodPressure.split("/");
                int systolic = Integer.parseInt(parts[0].trim());
                int diastolic = Integer.parseInt(parts[1].trim());

                if (systolic < 80) {
                    abnormals.add(new AbnormalVital("Pressão Arterial", bloodPressure + " mmHg",
                            "Sistólica abaixo de 80 mmHg (hipotensão)", "CRITICAL"));
                } else if (systolic > 160) {
                    abnormals.add(new AbnormalVital("Pressão Arterial", bloodPressure + " mmHg",
                            "Sistólica acima de 160 mmHg (hipertensão grave)", "CRITICAL"));
                } else if (systolic > 120) {
                    abnormals.add(new AbnormalVital("Pressão Arterial", bloodPressure + " mmHg",
                            "Sistólica acima de 120 mmHg (pré-hipertensão)", "WARNING"));
                }

                if (diastolic > 100) {
                    abnormals.add(new AbnormalVital("Pressão Arterial Diastólica", bloodPressure + " mmHg",
                            "Diastólica acima de 100 mmHg (hipertensão grave)", "CRITICAL"));
                } else if (diastolic > 80) {
                    abnormals.add(new AbnormalVital("Pressão Arterial Diastólica", bloodPressure + " mmHg",
                            "Diastólica acima de 80 mmHg (pré-hipertensão)", "WARNING"));
                }
            } catch (NumberFormatException ignored) {}
        }

        return abnormals;
    }
}
