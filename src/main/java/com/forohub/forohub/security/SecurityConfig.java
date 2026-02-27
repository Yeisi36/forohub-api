package com.forohub.forohub.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration  // Le dice a Spring que esta clase tiene configuraciones importantes
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Desactivamos CSRF porque las APIs REST no lo necesitan
                .csrf(csrf -> csrf.disable())

                // No guardamos sesiones (cada petición trae su propio token)
                .sessionManagement(s ->
                        s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Definimos quién puede acceder a qué
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth").permitAll()           // Login: acceso libre
                        .requestMatchers("/usuarios/registro").permitAll() // Registro: acceso libre
                                .requestMatchers(org.springframework.http.HttpMethod.GET,
                                        "/topicos", "/topicos/*", "/topicos/*/respuestas").permitAll()                   // Ver tópicos: acceso libre
                        .anyRequest().authenticated()

                        // Todo lo demás: necesita token
                )

                // Agregamos nuestro filtro JWT antes del filtro de autenticación de Spring
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Encriptador de contraseñas (BCrypt es el estándar de la industria)
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Permite que Spring maneje la autenticación
    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}