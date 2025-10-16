package org.seniorcare.identityaccess.application.commands.impl.employee;

import java.util.UUID;

public record DemoteEmployeeToUserCommand(
        UUID employeeId
) {
}
