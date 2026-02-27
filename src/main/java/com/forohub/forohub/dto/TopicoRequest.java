package com.forohub.forohub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// Lo que el usuario ENVÍA cuando quiere crear un tópico
@Data
public class TopicoRequest {

    @NotBlank(message = "El título no puede estar vacío")
    private String titulo;

    @NotBlank(message = "El mensaje no puede estar vacío")
    private String mensaje;

    @NotBlank(message = "El nombre del curso no puede estar vacío")
    private String nombreCurso;

    @NotNull(message = "Debes indicar el ID del usuario")
    private Long usuarioId;
}