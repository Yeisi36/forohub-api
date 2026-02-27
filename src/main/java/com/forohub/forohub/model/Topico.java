package com.forohub.forohub.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "topicos")
@Data
@NoArgsConstructor
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, length = 2000)  // Máximo 2000 caracteres para el mensaje
    private String mensaje;

    @Column(name = "nombre_curso", nullable = false)
    private String nombreCurso;

    // Se pone automáticamente la fecha y hora cuando se crea el tópico
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    // Relación: Cada tópico pertenece a un usuario (el autor)
    // Esto crea una columna "usuario_id" en la tabla topicos
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario autor;
}