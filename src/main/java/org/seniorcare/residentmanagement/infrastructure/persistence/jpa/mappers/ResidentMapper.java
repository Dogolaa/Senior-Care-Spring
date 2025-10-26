package org.seniorcare.residentmanagement.infrastructure.persistence.jpa.mappers;

import org.seniorcare.residentmanagement.domain.aggregates.Resident;
import org.seniorcare.residentmanagement.domain.vo.BloodType;
import org.seniorcare.residentmanagement.domain.vo.Cpf;
import org.seniorcare.residentmanagement.domain.vo.Gender;
import org.seniorcare.residentmanagement.domain.vo.Rg;
import org.seniorcare.residentmanagement.infrastructure.persistence.jpa.models.ResidentModel;
import org.springframework.stereotype.Component;

@Component
public class ResidentMapper {

    public ResidentMapper() {
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
        model.setResponsibleId(entity.getResponsibleId());
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

        return new Resident(
                model.getId(),
                model.getResponsibleId(),
                model.getName(),
                cpf,
                rg,
                model.getDateOfBirth(),
                gender,
                bloodType,
                model.isActive(),
                model.getAdmissionDate(),
                model.getRoom(),
                model.getCreatedAt(),
                model.getUpdatedAt(),
                model.getDeletedAt()
        );
    }
}