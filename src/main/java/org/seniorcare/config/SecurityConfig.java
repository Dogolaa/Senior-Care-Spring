package org.seniorcare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita a proteção contra CSRF. É comum em APIs REST stateless.
                .csrf(csrf -> csrf.disable())
                // Define as regras de autorização para as requisições HTTP.
                .authorizeHttpRequests(auth -> auth
                        // Para QUALQUER requisição (anyRequest), PERMITA TUDO (permitAll).
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}