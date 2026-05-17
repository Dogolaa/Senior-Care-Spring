package org.seniorcare.residentmanagement.application.commands.handlers.familyLink;

import org.seniorcare.residentmanagement.application.commands.impl.familyLink.SetPrimaryContactCommand;
import org.seniorcare.residentmanagement.domain.aggregates.Resident;
import org.seniorcare.residentmanagement.domain.repositories.IResidentsRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SetPrimaryContactCommandHandler {

    private final IResidentsRepository residentsRepository;

    public SetPrimaryContactCommandHandler(IResidentsRepository residentsRepository) {
        this.residentsRepository = residentsRepository;
    }

    @Transactional
    public void handle(SetPrimaryContactCommand command) {
        Resident resident = residentsRepository.findById(command.residentId())
                .orElseThrow(() -> new ResourceNotFoundException("Resident with id " + command.residentId() + " not found."));

        resident.setPrimaryContact(command.familyLinkId());

        residentsRepository.save(resident);
    }
}
