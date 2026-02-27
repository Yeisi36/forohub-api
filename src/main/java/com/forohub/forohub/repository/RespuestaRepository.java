package com.forohub.forohub.repository;

import com.forohub.forohub.model.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

    // Busca todas las respuestas de un tópico específico
    List<Respuesta> findByTopicoId(Long topicoId);
}