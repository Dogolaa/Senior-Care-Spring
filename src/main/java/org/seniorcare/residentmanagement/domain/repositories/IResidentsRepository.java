package org.seniorcare.residentmanagement.domain.repositories;

import org.seniorcare.residentmanagement.domain.aggregates.Resident;
import org.seniorcare.residentmanagement.domain.vo.Cpf;
import org.seniorcare.shared.domain.PageResult;
import org.seniorcare.shared.domain.Pagination;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IResidentsRepository {

    void save(Resident resident);

    Optional<Resident> findById(UUID id);

    Optional<Resident> findByCpf(Cpf cpf);

    boolean existsByCpf(Cpf cpf);

    PageResult<Resident> findAll(Pagination pagination);

    List<Resident> findByFamilyMemberId(UUID userId);
}
