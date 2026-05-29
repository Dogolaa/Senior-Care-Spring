package org.seniorcare.identityaccess.application.ports.output;

import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {

    String generateToken(UserDetails userDetails);
}
