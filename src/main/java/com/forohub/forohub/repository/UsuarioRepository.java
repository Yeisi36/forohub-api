package com.forohub.forohub.repository;

import com.forohub.forohub.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// JpaRepository<Usuario, Long> significa:
// - Maneja la tabla Usuario
// - La llave primaria es de tipo Long (número)
// Spring genera automáticamente los métodos básicos: save, findById, findAll, delete...
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método personalizado: buscar un usuario por su email
    // Spring entiende el nombre del método y crea el SQL automáticamente ✨
    Optional<Usuario> findByEmail(String email);
}