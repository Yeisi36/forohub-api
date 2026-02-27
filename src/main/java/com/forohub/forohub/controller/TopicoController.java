package com.forohub.forohub.controller;

import com.forohub.forohub.dto.TopicoRequest;
import com.forohub.forohub.dto.TopicoResponse;
import com.forohub.forohub.dto.TopicoUpdateRequest;
import com.forohub.forohub.service.TopicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/topicos")
@RequiredArgsConstructor
public class TopicoController {

    private final TopicoService topicoService;

    // GET /topicos → Listar todos los tópicos (PÚBLICO, no necesita token)
    @GetMapping
    public ResponseEntity<List<TopicoResponse>> listar() {
        List<TopicoResponse> topicos = topicoService.listarTodos();
        return ResponseEntity.ok(topicos);  // Status 200 OK
    }

    // POST /topicos → Crear nuevo tópico (PROTEGIDO, necesita token JWT)
    @PostMapping
    public ResponseEntity<TopicoResponse> crear(
            @Valid @RequestBody TopicoRequest request) {
        TopicoResponse creado = topicoService.crear(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)  // Status 201 Created
                .body(creado);
    }

    // DELETE /topicos/{id} → Eliminar un tópico (PROTEGIDO, necesita token JWT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        topicoService.eliminar(id);
        return ResponseEntity.ok().build();  // Status 200 OK
    }

    // GET /topicos/{id} → Ver un tópico específico (PÚBLICO)
    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponse> buscarPorId(@PathVariable Long id) {
        TopicoResponse topico = topicoService.buscarPorId(id);
        return ResponseEntity.ok(topico);  // 200 OK
    }

    // PUT /topicos/{id} → Editar un tópico (PROTEGIDO, necesita token JWT)
    @PutMapping("/{id}")
    public ResponseEntity<TopicoResponse> actualizar(
            @PathVariable Long id,
            @RequestBody TopicoUpdateRequest request) {
        TopicoResponse actualizado = topicoService.actualizar(id, request);
        return ResponseEntity.ok(actualizado);  // 200 OK
    }
}