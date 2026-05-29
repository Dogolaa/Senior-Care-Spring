package org.seniorcare.residentmanagement.application.ports.output;

import java.util.UUID;

public interface IUserExistencePort {

    boolean exists(UUID userId);
}
