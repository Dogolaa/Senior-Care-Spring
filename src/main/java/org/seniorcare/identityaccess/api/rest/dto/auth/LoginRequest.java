package org.seniorcare.identityaccess.api.rest.dto.auth;

public record LoginRequest(String email, String password) {
}