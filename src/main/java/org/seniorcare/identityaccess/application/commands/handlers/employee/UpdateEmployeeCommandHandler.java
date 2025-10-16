package org.seniorcare.identityaccess.application.commands.handlers.employee;

import org.seniorcare.identityaccess.application.commands.impl.employee.UpdateEmployeeCommand;
import org.seniorcare.identityaccess.application.dto.employee.EmployeeDTO;
import org.seniorcare.identityaccess.application.mappers.EmployeeDTOMapper;
import org.seniorcare.identityaccess.domain.entities.Doctor;
import org.seniorcare.identityaccess.domain.entities.Employee;
import org.seniorcare.identityaccess.domain.entities.Nurse;
import org.seniorcare.identityaccess.domain.repositories.IDoctorRepository;
import org.seniorcare.identityaccess.domain.repositories.IEmployeeRepository;
import org.seniorcare.identityaccess.domain.repositories.INurseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UpdateEmployeeCommandHandler {

    private final IEmployeeRepository employeeRepository;
    private final IDoctorRepository doctorRepository;
    private final INurseRepository nurseRepository;
    private final EmployeeDTOMapper employeeDTOMapper;

    public UpdateEmployeeCommandHandler(
            IEmployeeRepository employeeRepository,
            IDoctorRepository doctorRepository,
            INurseRepository nurseRepository,
            EmployeeDTOMapper employeeDTOMapper) {
        this.employeeRepository = employeeRepository;
        this.doctorRepository = doctorRepository;
        this.nurseRepository = nurseRepository;
        this.employeeDTOMapper = employeeDTOMapper;
    }

    @Transactional
    public EmployeeDTO handle(UpdateEmployeeCommand command) {
        Employee employeeToUpdate = employeeRepository.findById(command.employeeId())
                .orElseThrow(() -> new NoSuchElementException("Employee com id " + command.employeeId() + " não encontrado."));

        validateUniqueIdentifiers(command, employeeToUpdate);


        // TODO: [ARQUITETURA] Este bloco if/else viola o Princípio Aberto/Fechado (OCP).
        // Se o número de tipos de Employee crescer, refatorar para um Strategy Pattern,
        // onde cada tipo de funcionário teria sua própria EmployeeUpdateStrategy.
        // Para o escopo atual com 4 tipos, esta abordagem foi mantida pela simplicidade.
        if (command.admissionDate() != null) {
            employeeToUpdate.changeAdmissionDate(command.admissionDate());
        }
        if (employeeToUpdate instanceof Doctor doctor) {
            doctor.updateDetails(
                    command.crm(),
                    command.specialization(),
                    command.shift()
            );
        } else if (employeeToUpdate instanceof Nurse nurse) {
            nurse.updateDetails(
                    command.coren(),
                    command.specialization(),
                    command.shift()
            );
        } else {
            throw new UnsupportedOperationException("Operação de atualização não suportada para o tipo: " + employeeToUpdate.getClass().getSimpleName());
        }

        employeeRepository.save(employeeToUpdate);

        return employeeDTOMapper.toDTO(employeeToUpdate);
    }

    private void validateUniqueIdentifiers(UpdateEmployeeCommand command, Employee currentEmployee) {
        if (command.crm() != null && currentEmployee instanceof Doctor) {
            Optional<Doctor> existing = doctorRepository.findByCrm(command.crm());
            if (existing.isPresent() && !existing.get().getId().equals(currentEmployee.getId())) {
                throw new IllegalStateException("CRM " + command.crm() + " já está em uso por outro funcionário.");
            }
        }

        if (command.coren() != null && currentEmployee instanceof Nurse) {
            Optional<Nurse> existing = nurseRepository.findByCoren(command.coren());
            if (existing.isPresent() && !existing.get().getId().equals(currentEmployee.getId())) {
                throw new IllegalStateException("COREN " + command.coren() + " já está em uso por outro funcionário.");
            }
        }
    }
}