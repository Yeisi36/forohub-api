package com.forohub.forohub.dto;

import lombok.Data;
import java.time.LocalDateTime;

// Lo que la API DEVUELVE cuando alguien pide ver tópicos
@Data
public class TopicoResponse {
    private Long id;
    private String titulo;
    private String mensaje;
    private String nombreCurso;
    private LocalDateTime fechaCreacion;
    private String autorNombre;  // Solo mostramos el nombre, no toda la info del usuario
}