package org.seniorcare.residentmanagement.application.commands.handlers.resident;

import org.seniorcare.residentmanagement.application.commands.impl.resident.UpdateResidentCommand;
import org.seniorcare.residentmanagement.domain.aggregates.Resident;
import org.seniorcare.residentmanagement.domain.repositories.IResidentsRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UpdateResidentCommandHandler {

    private final IResidentsRepository residentsRepository;

    public UpdateResidentCommandHandler(IResidentsRepository residentsRepository) {
        this.residentsRepository = residentsRepository;
    }

    @Transactional
    public UUID handle(UpdateResidentCommand command) {
        Resident resident = residentsRepository.findById(command.id())
                .orElseThrow(() -> new ResourceNotFoundException("Resident with id " + command.id() + " not found."));

        resident.update(command.name(), command.gender(), command.bloodType(), command.room());

        residentsRepository.save(resident);

        return resident.getId();
    }
}
