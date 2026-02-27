package com.forohub.forohub.controller;

import com.forohub.forohub.dto.RespuestaRequest;
import com.forohub.forohub.dto.RespuestaResponse;
import com.forohub.forohub.service.RespuestaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/topicos/{topicoId}/respuestas")
@RequiredArgsConstructor
public class RespuestaController {

    private final RespuestaService respuestaService;

    // GET /topicos/{topicoId}/respuestas → Ver respuestas de un tópico (PÚBLICO)
    @GetMapping
    public ResponseEntity<List<RespuestaResponse>> listar(
            @PathVariable Long topicoId) {
        return ResponseEntity.ok(respuestaService.listarPorTopico(topicoId));
    }

    // POST /topicos/{topicoId}/respuestas → Responder un tópico (PROTEGIDO)
    @PostMapping
    public ResponseEntity<RespuestaResponse> crear(
            @PathVariable Long topicoId,
            @Valid @RequestBody RespuestaRequest request) {
        RespuestaResponse creada = respuestaService.crear(topicoId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }
}