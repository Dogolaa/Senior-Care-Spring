package org.seniorcare.residentmanagement.infrastructure.persistence.jpa.mappers;

import org.seniorcare.residentmanagement.domain.aggregates.Resident;
import org.seniorcare.residentmanagement.domain.entities.FamilyLink;
import org.seniorcare.residentmanagement.domain.vo.BloodType;
import org.seniorcare.residentmanagement.domain.vo.Cpf;
import org.seniorcare.residentmanagement.domain.vo.Gender;
import org.seniorcare.residentmanagement.domain.vo.Rg;
import org.seniorcare.residentmanagement.infrastructure.persistence.jpa.models.FamilyLinkModel;
import org.seniorcare.residentmanagement.infrastructure.persistence.jpa.models.ResidentModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResidentMapper {

    private final FamilyLinkMapper familyLinkMapper;

    public ResidentMapper(FamilyLinkMapper familyLinkMapper) {
        this.familyLinkMapper = familyLinkMapper;
    }

    public ResidentModel toModel(Resident entity) {
        if (entity == null) return null;

        ResidentModel model = new ResidentModel();

        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setCpf(entity.getCpf() != null ? entity.getCpf().CPF() : null);
        model.setRg(entity.getRg() != null ? entity.getRg().getNumero() : null);
        model.setDateOfBirth(entity.getDateOfBirth());
        model.setGender(entity.getGender() != null ? entity.getGender().name() : null);
        model.setBloodType(entity.getBloodType() != null ? entity.getBloodType().name() : null);
        model.setActive(entity.isActive());
        model.setAdmissionDate(entity.getAdmissionDate());
        model.setRoom(entity.getRoom());
        if (entity.getAllergies() != null) {
            model.setAllergies(entity.getAllergies());
        }
        if (entity.getFamilyLinks() != null) {
            List<FamilyLinkModel> linkModels = entity.getFamilyLinks().stream()
                    .map(linkEntity -> {
                        FamilyLinkModel linkModel = familyLinkMapper.toModel(linkEntity);
                        linkModel.setResident(model);
                        return linkModel;
                    })
                    .collect(Collectors.toList());
            model.setFamilyLinks(linkModels);
        }

        model.setCreatedAt(entity.getCreatedAt());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setDeletedAt(entity.getDeletedAt());

        return model;
    }


    public Resident toEntity(ResidentModel model) {
        if (model == null) return null;

        Cpf cpf = (model.getCpf() != null) ? new Cpf(model.getCpf()) : null;
        Rg rg = (model.getRg() != null) ? new Rg(model.getRg()) : null;

        Gender gender = (model.getGender() != null) ? Gender.valueOf(model.getGender()) : null;
        BloodType bloodType = (model.getBloodType() != null) ? BloodType.valueOf(model.getBloodType()) : null;

        List<String> allergies = (model.getAllergies() != null) ? model.getAllergies() : null;

        List<FamilyLink> familyLinks = null;
        if (model.getFamilyLinks() != null) {
            familyLinks = model.getFamilyLinks().stream()
                    .map(familyLinkMapper::toEntity)
                    .collect(Collectors.toList());
        }

        return new Resident(
                model.getId(),
                model.getName(),
                cpf,
                rg,
                model.getDateOfBirth(),
                gender,
                bloodType,
                allergies,
                model.isActive(),
                model.getAdmissionDate(),
                model.getRoom(),
                familyLinks,
                model.getCreatedAt(),
                model.getUpdatedAt(),
                model.getDeletedAt()
        );
    }
}