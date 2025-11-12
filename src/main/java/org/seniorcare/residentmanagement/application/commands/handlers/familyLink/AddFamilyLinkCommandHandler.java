package org.seniorcare.residentmanagement.application.commands.handlers.familyLink;

import org.seniorcare.identityaccess.domain.repositories.IUserRepository;
import org.seniorcare.residentmanagement.application.commands.impl.familyLink.AddFamilyLinkCommand;
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

    private final IUserRepository userRepository;
    private final IResidentsRepository residentRepository;
    private final EventDispatcher eventDispatcher;

    public AddFamilyLinkCommandHandler(
            IUserRepository userRepository,
            IResidentsRepository residentRepository,
            EventDispatcher eventDispatcher) {
        this.residentRepository = residentRepository;
        this.userRepository = userRepository;
        this.eventDispatcher = eventDispatcher;
    }

    @Transactional
    public UUID handle(AddFamilyLinkCommand command) {

        userRepository.findById(command.familyMemberId())
                .orElseThrow(() -> new NoSuchElementException("Usuário (familiar) com ID " + command.familyMemberId() + " não encontrado."));

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