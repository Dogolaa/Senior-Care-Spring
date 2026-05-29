package org.seniorcare.health.application.ports.output;

import org.seniorcare.health.domain.vo.AbnormalVital;

import java.util.List;
import java.util.UUID;

public interface IVitalAlertPort {

    void notifyAbnormalVitals(UUID residentId, List<AbnormalVital> abnormals);
}
