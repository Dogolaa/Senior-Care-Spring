package org.seniorcare.residentmanagement.application.commands.handlers.resident;

import org.seniorcare.residentmanagement.application.commands.impl.resident.AddAllergyCommand;
import org.seniorcare.residentmanagement.domain.aggregates.Resident;
import org.seniorcare.residentmanagement.domain.repositories.IResidentsRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddAllergyCommandHandler {

    private final IResidentsRepository residentsRepository;

    public AddAllergyCommandHandler(IResidentsRepository residentsRepository) {
        this.residentsRepository = residentsRepository;
    }

    @Transactional
    public void handle(AddAllergyCommand command) {
        Resident resident = residentsRepository.findById(command.residentId())
                .orElseThrow(() -> new ResourceNotFoundException("Resident with id " + command.residentId() + " not found."));

        resident.addAllergy(command.allergyDescription());

        residentsRepository.save(resident);
    }
}
