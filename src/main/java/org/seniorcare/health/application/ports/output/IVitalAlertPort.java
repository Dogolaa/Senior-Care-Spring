package org.seniorcare.health.application.ports.output;

import java.util.List;
import java.util.UUID;

public interface IVitalAlertPort {

    record AbnormalVital(String name, String value, String reason) {}

    void notifyAbnormalVitals(UUID residentId, List<AbnormalVital> abnormals);
}
