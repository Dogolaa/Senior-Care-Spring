package org.seniorcare.identityaccess.application.security;

import java.util.UUID;

public interface AuthenticatedPrincipal {
    UUID getId();
    String getName();
    String getEmail();
    String getRoleName();
    boolean isMustChangePassword();
}
