package com.forohub.forohub.exception;

// Esta es nuestra excepción personalizada
// Cuando algo no se encuentra, lanzamos esta en lugar del error genérico de Spring
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}