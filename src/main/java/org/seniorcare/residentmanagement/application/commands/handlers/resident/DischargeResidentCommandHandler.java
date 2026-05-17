package org.seniorcare.residentmanagement.application.commands.handlers.resident;

import org.seniorcare.residentmanagement.application.commands.impl.resident.DischargeResidentCommand;
import org.seniorcare.residentmanagement.domain.aggregates.Resident;
import org.seniorcare.residentmanagement.domain.repositories.IResidentsRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DischargeResidentCommandHandler {

    private final IResidentsRepository residentsRepository;

    public DischargeResidentCommandHandler(IResidentsRepository residentsRepository) {
        this.residentsRepository = residentsRepository;
    }

    @Transactional
    public void handle(DischargeResidentCommand command) {
        Resident resident = residentsRepository.findById(command.id())
                .orElseThrow(() -> new ResourceNotFoundException("Resident with id " + command.id() + " not found."));

        resident.recordDischarge();

        residentsRepository.save(resident);
    }
}
