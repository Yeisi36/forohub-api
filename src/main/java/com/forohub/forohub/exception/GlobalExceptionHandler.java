package com.forohub.forohub.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.*;

// @RestControllerAdvice significa: "intercepta todos los errores de todos los controladores"
// Es como un guardián que atrapa todos los errores antes de que lleguen al usuario
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Cuando un recurso no existe (tópico, usuario, etc.)
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> manejarNoEncontrado(
            RecursoNoEncontradoException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)  // 404
                .body(Map.of("error", ex.getMessage()));
    }

    // Cuando los datos enviados no son válidos (@NotBlank, @Email, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidacion(
            MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)  // 400
                .body(errores);
    }

    // Cualquier otro error inesperado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> manejarGeneral(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
                .body(Map.of("error", "Ocurrió un error interno: " + ex.getMessage()));
    }
}