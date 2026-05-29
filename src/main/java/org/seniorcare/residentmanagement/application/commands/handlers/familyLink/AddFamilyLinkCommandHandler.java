package org.seniorcare.residentmanagement.application.commands.handlers.familyLink;

import org.seniorcare.residentmanagement.application.commands.impl.familyLink.AddFamilyLinkCommand;
import org.seniorcare.residentmanagement.application.ports.output.IUserExistencePort;
import org.seniorcare.residentmanagement.domain.aggregates.Resident;
import org.seniorcare.residentmanagement.domain.entities.FamilyLink;
import org.seniorcare.residentmanagement.domain.repositories.IResidentsRepository;
import org.seniorcare.shared.application.EventDispatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class AddFamilyLinkCommandHandler {

    private final IUserExistencePort userExistencePort;
    private final IResidentsRepository residentRepository;
    private final EventDispatcher eventDispatcher;

    public AddFamilyLinkCommandHandler(
            IUserExistencePort userExistencePort,
            IResidentsRepository residentRepository,
            EventDispatcher eventDispatcher) {
        this.userExistencePort = userExistencePort;
        this.residentRepository = residentRepository;
        this.eventDispatcher = eventDispatcher;
    }

    @Transactional
    public UUID handle(AddFamilyLinkCommand command) {

        if (!userExistencePort.exists(command.familyMemberId())) {
            throw new NoSuchElementException("Usuário (familiar) com ID " + command.familyMemberId() + " não encontrado.");
        }

        Resident resident = residentRepository.findById(command.residentId())
                .orElseThrow(() -> new NoSuchElementException("Residente com ID " + command.residentId() + " não encontrado."));

        FamilyLink newFamilyLink = resident.addFamilyLink(
                command.familyMemberId(),
                command.relationship(),
                command.isPrimaryContact()
        );

        residentRepository.save(resident);

        List<Object> events = resident.getDomainEvents();
        eventDispatcher.dispatch(events);
        resident.clearDomainEvents();

        return newFamilyLink.getId();
    }
}
