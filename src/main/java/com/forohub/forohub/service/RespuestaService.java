package com.forohub.forohub.service;

import com.forohub.forohub.dto.RespuestaRequest;
import com.forohub.forohub.dto.RespuestaResponse;
import com.forohub.forohub.exception.RecursoNoEncontradoException;
import com.forohub.forohub.model.Respuesta;
import com.forohub.forohub.model.Topico;
import com.forohub.forohub.model.Usuario;
import com.forohub.forohub.repository.RespuestaRepository;
import com.forohub.forohub.repository.TopicoRepository;
import com.forohub.forohub.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RespuestaService {

    private final RespuestaRepository respuestaRepository;
    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;

    // CREAR una respuesta para un tópico
    public RespuestaResponse crear(Long topicoId, RespuestaRequest request) {

        // Verificamos que el tópico existe
        Topico topico = topicoRepository.findById(topicoId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un tópico con ID: " + topicoId));

        // Verificamos que el usuario existe
        Usuario autor = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un usuario con ID: " + request.getUsuarioId()));

        Respuesta respuesta = new Respuesta();
        respuesta.setMensaje(request.getMensaje());
        respuesta.setTopico(topico);
        respuesta.setAutor(autor);

        return convertirAResponse(respuestaRepository.save(respuesta));
    }

    // LISTAR todas las respuestas de un tópico
    public List<RespuestaResponse> listarPorTopico(Long topicoId) {

        // Verificamos que el tópico existe
        if (!topicoRepository.existsById(topicoId)) {
            throw new RecursoNoEncontradoException(
                    "No existe un tópico con ID: " + topicoId);
        }

        return respuestaRepository.findByTopicoId(topicoId)
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    // Convierte Respuesta a RespuestaResponse
    private RespuestaResponse convertirAResponse(Respuesta respuesta) {
        RespuestaResponse response = new RespuestaResponse();
        response.setId(respuesta.getId());
        response.setMensaje(respuesta.getMensaje());
        response.setFechaCreacion(respuesta.getFechaCreacion());
        response.setAutorNombre(respuesta.getAutor().getNombre());
        response.setTopicoId(respuesta.getTopico().getId());
        return response;
    }
}