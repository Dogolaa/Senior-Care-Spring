package org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers;

import org.seniorcare.identityaccess.domain.entities.Employee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class EmployeeMapperFactory {

    private final Map<Class<? extends Employee>, EmployeeSubtypeMapper> mappers;

    public EmployeeMapperFactory(List<EmployeeSubtypeMapper> mapperList) {
        this.mappers = mapperList.stream()
                .collect(Collectors.toMap(EmployeeSubtypeMapper::getEntityClass, Function.identity()));
    }


    public EmployeeSubtypeMapper getMapper(Employee entity) {
        EmployeeSubtypeMapper mapper = mappers.get(entity.getClass());
        if (mapper == null) {
            throw new IllegalArgumentException("No mapper found for entity type: " + entity.getClass().getName());
        }
        return mapper;
    }
}