package org.seniorcare.identityaccess.application.services;

import org.seniorcare.identityaccess.api.rest.dto.auth.LoginRequest;
import org.seniorcare.identityaccess.api.rest.dto.auth.LoginResponse;
import org.seniorcare.identityaccess.domain.repositories.IRoleRepository;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.UserModel;
import org.seniorcare.identityaccess.infrastructure.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final IRoleRepository roleRepository; // Para buscar o nome do Role

    public AuthenticationService(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            IRoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
    }

    public LoginResponse login(LoginRequest request) {
        // 1. O AuthenticationManager valida o usuário e senha
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        // 2. Se chegou aqui, o login é válido. Pegamos o UserModel que o Spring autenticou.
        var userModel = (UserModel) authentication.getPrincipal();

        // 3. Geramos o token JWT
        String token = jwtService.generateToken(userModel);

        // 4. Montamos a resposta
        String roleName = userModel.getRole().getName(); // Pegamos direto do UserModel

        return new LoginResponse(
                token,
                userModel.getId(),
                userModel.getName(),
                userModel.getEmail(),
                roleName
        );
    }
}