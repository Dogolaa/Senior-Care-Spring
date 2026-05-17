package org.seniorcare.residentmanagement.domain.repositories;

import org.seniorcare.residentmanagement.domain.aggregates.Resident;
import org.seniorcare.residentmanagement.domain.vo.Cpf;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IResidentsRepository {

    void save(Resident resident);

    Optional<Resident> findById(UUID id);

    Optional<Resident> findByCpf(Cpf cpf);

    boolean existsByCpf(Cpf cpf);

    Page<Resident> findAll(Pageable pageable);
}
