package com.forohub.forohub.service;

import com.forohub.forohub.model.Usuario;
import com.forohub.forohub.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// Implementamos UserDetailsService para que Spring Security sepa cómo cargar usuarios
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder encoder;

    // Spring Security llama a este método para verificar si el usuario existe
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "No existe un usuario con el email: " + email));

        // Retorna un objeto que Spring Security entiende
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getContrasena())
                .roles("USER")
                .build();
    }

    // Método para registrar nuevos usuarios
    public Usuario registrar(String nombre, String email, String contrasena) {
        Usuario nuevo = new Usuario();
        nuevo.setNombre(nombre);
        nuevo.setEmail(email);
        nuevo.setContrasena(encoder.encode(contrasena));  // ¡Siempre encriptada!
        return usuarioRepository.save(nuevo);
    }
}
