package com.forohub.forohub.controller;

import com.forohub.forohub.model.Usuario;
import com.forohub.forohub.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // POST /usuarios/registro → Registrar un nuevo usuario
    // Este endpoint es público (no necesita token)
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody Map<String, String> datos) {
        String nombre = datos.get("nombre");
        String email = datos.get("email");
        String contrasena = datos.get("contrasena");

        Usuario nuevo = usuarioService.registrar(nombre, email, contrasena);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "id", nuevo.getId(),
                        "nombre", nuevo.getNombre(),
                        "email", nuevo.getEmail(),
                        "mensaje", "Usuario registrado exitosamente"
                ));
    }
}