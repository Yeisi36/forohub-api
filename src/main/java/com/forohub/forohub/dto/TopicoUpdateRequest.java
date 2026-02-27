package com.forohub.forohub.dto;

import lombok.Data;

// No usamos @NotBlank porque el usuario puede enviar solo los campos que quiere cambiar
// Si envía null en un campo, no lo tocamos
@Data
public class TopicoUpdateRequest {
    private String titulo;
    private String mensaje;
    private String nombreCurso;
}