package org.seniorcare.health.application.commands.handlers;

import org.seniorcare.health.application.commands.impl.PushVitalsCommand;
import org.seniorcare.health.application.ports.output.IVitalAlertPort;
import org.seniorcare.health.domain.aggregates.HealthRecord;
import org.seniorcare.health.domain.repositories.IHealthRecordRepository;
import org.seniorcare.health.domain.vo.AbnormalVital;
import org.seniorcare.health.domain.vo.VitalThresholds;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PushVitalsCommandHandler {

    private final IHealthRecordRepository healthRecordRepository;
    private final IVitalAlertPort vitalAlertPort;

    public PushVitalsCommandHandler(IHealthRecordRepository healthRecordRepository,
                                    IVitalAlertPort vitalAlertPort) {
        this.healthRecordRepository = healthRecordRepository;
        this.vitalAlertPort = vitalAlertPort;
    }

    @Transactional
    public void handle(PushVitalsCommand command) {
        HealthRecord record = healthRecordRepository.findByResidentId(command.residentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Health record for resident " + command.residentId() + " not found."));

        record.recordVitals(
                command.recordedById(),
                command.heartRate(),
                command.saturation(),
                command.bloodPressure(),
                command.temperature(),
                command.source()
        );

        healthRecordRepository.save(record);

        List<AbnormalVital> abnormals = VitalThresholds.evaluate(
                command.heartRate(),
                command.saturation(),
                command.bloodPressure(),
                command.temperature()
        );

        if (!abnormals.isEmpty()) {
            vitalAlertPort.notifyAbnormalVitals(command.residentId(), abnormals);
        }
    }
}
