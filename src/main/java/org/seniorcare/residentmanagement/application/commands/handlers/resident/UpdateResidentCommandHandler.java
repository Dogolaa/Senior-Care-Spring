package org.seniorcare.residentmanagement.application.commands.handlers.resident;

import org.seniorcare.residentmanagement.application.commands.impl.resident.UpdateResidentCommand;
import org.seniorcare.residentmanagement.application.dto.resident.FamilyLinkDTO;
import org.seniorcare.residentmanagement.application.dto.resident.ResidentDTO;
import org.seniorcare.residentmanagement.domain.aggregates.Resident;
import org.seniorcare.residentmanagement.domain.entities.FamilyLink;
import org.seniorcare.residentmanagement.domain.repositories.IResidentsRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UpdateResidentCommandHandler {

    private final IResidentsRepository residentsRepository;

    public UpdateResidentCommandHandler(IResidentsRepository residentsRepository) {
        this.residentsRepository = residentsRepository;
    }

    @Transactional
    public ResidentDTO handle(UpdateResidentCommand command) {
        Resident resident = residentsRepository.findById(command.id())
                .orElseThrow(() -> new ResourceNotFoundException("Resident with id " + command.id() + " not found."));

        resident.update(command.name(), command.gender(), command.bloodType(), command.room());

        residentsRepository.save(resident);

        return toDTO(resident);
    }

    private ResidentDTO toDTO(Resident resident) {
        List<FamilyLinkDTO> familyLinkDTOs = resident.getFamilyLinks().stream()
                .map(this::toFamilyLinkDTO)
                .collect(Collectors.toList());

        return new ResidentDTO(
                resident.getId(),
                resident.getName(),
                resident.getCpf() != null ? resident.getCpf().CPF() : null,
                resident.getRg() != null ? resident.getRg().getNumero() : null,
                resident.getDateOfBirth(),
                resident.getGender() != null ? resident.getGender().name() : null,
                resident.getBloodType() != null ? resident.getBloodType().name() : null,
                resident.isActive(),
                resident.getAdmissionDate(),
                resident.getRoom(),
                resident.getAllergies(),
                familyLinkDTOs,
                resident.getCreatedAt(),
                resident.getUpdatedAt()
        );
    }

    private FamilyLinkDTO toFamilyLinkDTO(FamilyLink familyLink) {
        return new FamilyLinkDTO(
                familyLink.getId(),
                familyLink.getFamilyMemberId(),
                familyLink.getRelationship(),
                familyLink.isPrimaryContact(),
                familyLink.getCreatedAt(),
                familyLink.getUpdatedAt()
        );
    }
}
