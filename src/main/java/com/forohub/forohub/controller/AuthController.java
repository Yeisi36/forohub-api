package com.forohub.forohub.controller;

import com.forohub.forohub.dto.LoginRequest;
import com.forohub.forohub.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController           // Este es un controlador REST (devuelve JSON)
@RequestMapping("/auth")  // Todas las rutas de esta clase empiezan con /auth
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    // POST /auth → Endpoint de login
    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        // Intenta autenticar al usuario con email y contraseña
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getContrasena()
                )
        );

        // Si llega aquí, las credenciales son correctas → generamos el token
        String token = jwtService.generarToken(request.getEmail());

        // Devolvemos el token en formato JSON: {"token": "eyJ..."}
        return ResponseEntity.ok(Map.of("token", token));
    }
}