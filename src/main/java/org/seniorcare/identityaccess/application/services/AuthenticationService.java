package org.seniorcare.identityaccess.application.services;

import org.seniorcare.identityaccess.api.rest.dto.auth.LoginRequest;
import org.seniorcare.identityaccess.api.rest.dto.auth.LoginResponse;
import org.seniorcare.identityaccess.application.security.AuthenticatedPrincipal;
import org.seniorcare.identityaccess.infrastructure.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationService(
            AuthenticationManager authenticationManager,
            JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        var userDetails = (UserDetails) authentication.getPrincipal();
        var principal = (AuthenticatedPrincipal) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails);

        return new LoginResponse(
                token,
                principal.getId(),
                principal.getName(),
                principal.getEmail(),
                principal.getRoleName(),
                principal.isMustChangePassword()
        );
    }
}
