package org.seniorcare.residentmanagement.application.commands.handlers.resident;

import org.seniorcare.identityaccess.domain.repositories.IUserRepository;
import org.seniorcare.residentmanagement.application.commands.impl.resident.AdmitResidentCommand;
import org.seniorcare.residentmanagement.domain.aggregates.Resident;
import org.seniorcare.residentmanagement.domain.repositories.IResidentsRepository;
import org.seniorcare.residentmanagement.domain.vo.Cpf;
import org.seniorcare.shared.application.EventDispatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class AdmitResidentCommandHandler {

    private final IResidentsRepository residentRepository;
    private final IUserRepository userRepository;
    private final EventDispatcher eventDispatcher;

    public AdmitResidentCommandHandler(
            IResidentsRepository residentRepository,
            IUserRepository userRepository,
            EventDispatcher eventDispatcher) {
        this.residentRepository = residentRepository;
        this.userRepository = userRepository;
        this.eventDispatcher = eventDispatcher;
    }

    @Transactional
    public UUID handle(AdmitResidentCommand command) {

        Cpf cpfVO = new Cpf(command.cpfValue());
        if (residentRepository.findByCpf(cpfVO).isPresent()) {
            throw new IllegalStateException("Já existe um residente com o CPF informado.");
        }

        userRepository.findById(command.responsibleId())
                .orElseThrow(() -> new NoSuchElementException("Usuário responsável com ID " + command.responsibleId() + " não encontrado."));

        Resident newResident = Resident.admit(
                command.responsibleId(),
                command.name(),
                command.cpfValue(),
                command.rgValue(),
                command.dateOfBirth(),
                command.gender(),
                command.bloodType(),
                command.room()
        );

        residentRepository.save(newResident);

        List<Object> events = newResident.getDomainEvents();

        eventDispatcher.dispatch(events);

        newResident.clearDomainEvents();

        return newResident.getId();
    }
}