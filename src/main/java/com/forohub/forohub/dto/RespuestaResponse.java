package com.forohub.forohub.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RespuestaResponse {
    private Long id;
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private String autorNombre;
    private Long topicoId;
}