package org.seniorcare.identityaccess.application.services;

import org.seniorcare.identityaccess.application.commands.impl.auth.LoginCommand;
import org.seniorcare.identityaccess.application.dto.auth.LoginResult;
import org.seniorcare.identityaccess.application.ports.output.IJwtService;
import org.seniorcare.identityaccess.application.security.AuthenticatedPrincipal;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final IJwtService jwtService;

    public AuthenticationService(
            AuthenticationManager authenticationManager,
            IJwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResult login(LoginCommand command) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(command.email(), command.password())
        );

        var userDetails = (UserDetails) authentication.getPrincipal();
        var principal = (AuthenticatedPrincipal) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails);

        return new LoginResult(
                token,
                principal.getId(),
                principal.getName(),
                principal.getEmail(),
                principal.getRoleName(),
                principal.isMustChangePassword()
        );
    }
}
