package org.seniorcare.residentmanagement.application.commands.handlers.resident;

import org.seniorcare.residentmanagement.application.commands.impl.resident.AdmitResidentCommand;
import org.seniorcare.residentmanagement.domain.aggregates.Resident;
import org.seniorcare.residentmanagement.domain.repositories.IResidentsRepository;
import org.seniorcare.residentmanagement.domain.vo.Cpf;
import org.seniorcare.shared.application.EventDispatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AdmitResidentCommandHandler {

    private final IResidentsRepository residentRepository;
    private final EventDispatcher eventDispatcher;

    public AdmitResidentCommandHandler(
            IResidentsRepository residentRepository,
            EventDispatcher eventDispatcher) {
        this.residentRepository = residentRepository;
        this.eventDispatcher = eventDispatcher;
    }

    @Transactional
    public UUID handle(AdmitResidentCommand command) {

        Cpf cpfVO = new Cpf(command.cpfValue());
        if (residentRepository.findByCpf(cpfVO).isPresent()) {
            throw new IllegalStateException("Já existe um residente com o CPF informado.");
        }

        Resident newResident = Resident.admit(
                command.name(),
                command.cpfValue(),
                command.rgValue(),
                command.dateOfBirth(),
                command.gender(),
                command.bloodType(),
                command.initialAllergies(),
                command.room()
        );

        residentRepository.save(newResident);

        List<Object> events = newResident.getDomainEvents();

        eventDispatcher.dispatch(events);

        newResident.clearDomainEvents();

        return newResident.getId();
    }
}