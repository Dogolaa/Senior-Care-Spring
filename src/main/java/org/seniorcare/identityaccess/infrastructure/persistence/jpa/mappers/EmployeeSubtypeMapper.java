package org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers;

import org.seniorcare.identityaccess.domain.entities.Employee;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.EmployeeModel;


public interface EmployeeSubtypeMapper<E extends Employee, M extends EmployeeModel> {

    Class<E> getEntityClass();

    M toModel(E entity);
}