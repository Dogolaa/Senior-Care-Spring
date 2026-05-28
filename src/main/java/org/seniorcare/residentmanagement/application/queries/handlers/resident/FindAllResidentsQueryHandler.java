package org.seniorcare.residentmanagement.application.queries.handlers.resident;

import org.seniorcare.residentmanagement.application.dto.resident.FamilyLinkDTO;
import org.seniorcare.residentmanagement.application.dto.resident.ResidentDTO;
import org.seniorcare.residentmanagement.application.queries.impl.resident.FindAllResidentsQuery;
import org.seniorcare.residentmanagement.domain.aggregates.Resident;
import org.seniorcare.residentmanagement.domain.entities.FamilyLink;
import org.seniorcare.residentmanagement.domain.repositories.IResidentsRepository;
import org.seniorcare.shared.domain.PageResult;
import org.seniorcare.shared.domain.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindAllResidentsQueryHandler {

    private final IResidentsRepository residentsRepository;

    public FindAllResidentsQueryHandler(IResidentsRepository residentsRepository) {
        this.residentsRepository = residentsRepository;
    }

    @Transactional(readOnly = true)
    public Page<ResidentDTO> handle(FindAllResidentsQuery query) {
        Pagination pagination = new Pagination(
                query.pageable().getPageNumber(),
                query.pageable().getPageSize()
        );
        PageResult<ResidentDTO> result = residentsRepository.findAll(pagination).map(this::toDTO);
        return new PageImpl<>(
                result.content(),
                PageRequest.of(result.currentPage(), result.pageSize()),
                result.totalElements()
        );
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
