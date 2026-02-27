package com.forohub.forohub.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Entity le dice a Spring: "esta clase es una tabla en la base de datos"
@Entity
@Table(name = "usuarios")  // El nombre de la tabla en PostgreSQL será "usuarios"
@Data                        // Lombok genera automáticamente getters y setters
@NoArgsConstructor           // Lombok genera un constructor vacío
public class Usuario {

    @Id  // Esta es la llave primaria (identificador único)
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Se auto-incrementa: 1, 2, 3...
    private Long id;

    @Column(nullable = false)  // No puede estar vacío
    private String nombre;

    @Column(nullable = false, unique = true)  // No puede repetirse el email
    private String email;

    @Column(nullable = false)
    private String contrasena;  // Se guardará encriptada, no en texto plano
}