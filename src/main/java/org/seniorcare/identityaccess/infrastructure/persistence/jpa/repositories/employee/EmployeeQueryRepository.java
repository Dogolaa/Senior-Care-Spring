package org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories.employee;

import org.seniorcare.identityaccess.application.dto.employee.EmployeeDetailsDTO;
import org.seniorcare.identityaccess.application.ports.IEmployeeQueryPort;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.EmployeeModel;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.projections.EmployeeDetailsProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeQueryRepository extends JpaRepository<EmployeeModel, UUID>, IEmployeeQueryPort {

    String EMPLOYEE_DETAILS_QUERY = """
                SELECT new org.seniorcare.identityaccess.infrastructure.persistence.jpa.projections.EmployeeDetailsProjection(
                    u.id,
                    e.id,
                    u.name,
                    u.email,
                    u.phone,
                    e.admissionDate,
                    u.isActive,
                    e.createdAt,
                    CASE
                        WHEN n.id IS NOT NULL THEN 'NURSE'
                        WHEN d.id IS NOT NULL THEN 'DOCTOR'
                        WHEN m.id IS NOT NULL THEN 'MANAGER'
                        ELSE 'EMPLOYEE'
                    END,
                    n.coren,
                    d.crm,
                    m.department,
                    n.specialization,
                    d.specialization,
                    m.shift,
                    n.shift,
                    d.shift
                )
                FROM EmployeeModel e
                JOIN e.user u
                LEFT JOIN NurseModel n ON e.id = n.id
                LEFT JOIN DoctorModel d ON e.id = d.id
                LEFT JOIN ManagerModel m ON e.id = m.id
            """;

    @Query(EMPLOYEE_DETAILS_QUERY + " WHERE e.id = :id")
    Optional<EmployeeDetailsProjection> findProjectionById(@Param("id") UUID id);

    @Query(value = EMPLOYEE_DETAILS_QUERY,
            countQuery = "SELECT COUNT(e) FROM EmployeeModel e")
    Page<EmployeeDetailsProjection> findAllProjections(Pageable pageable);

    @Override
    default Optional<EmployeeDetailsDTO> findDetailsById(UUID id) {
        return findProjectionById(id).map(EmployeeQueryRepository::toDTO);
    }

    @Override
    default Page<EmployeeDetailsDTO> findAllDetails(Pageable pageable) {
        return findAllProjections(pageable).map(EmployeeQueryRepository::toDTO);
    }

    private static EmployeeDetailsDTO toDTO(EmployeeDetailsProjection p) {
        return new EmployeeDetailsDTO(
                p.userId(),
                p.employeeId(),
                p.name(),
                p.email(),
                p.phone(),
                p.admissionDate(),
                p.isActive(),
                p.createdAt(),
                p.role(),
                p.coren(),
                p.crm(),
                p.department(),
                p.nurseSpecialization(),
                p.doctorSpecialization(),
                p.managerShift(),
                p.nurseShift(),
                p.doctorShift()
        );
    }
}
