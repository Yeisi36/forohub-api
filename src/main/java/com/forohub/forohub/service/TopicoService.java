package com.forohub.forohub.service;

import com.forohub.forohub.dto.TopicoRequest;
import com.forohub.forohub.dto.TopicoResponse;
import com.forohub.forohub.dto.TopicoUpdateRequest;
import com.forohub.forohub.exception.RecursoNoEncontradoException;
import com.forohub.forohub.model.Topico;
import com.forohub.forohub.model.Usuario;
import com.forohub.forohub.repository.TopicoRepository;
import com.forohub.forohub.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;

    // LISTAR TODOS los tópicos
    public List<TopicoResponse> listarTodos() {
        return topicoRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    // CREAR un nuevo tópico
    public TopicoResponse crear(TopicoRequest request) {
        Usuario autor = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un usuario con ID: " + request.getUsuarioId()));

        Topico topico = new Topico();
        topico.setTitulo(request.getTitulo());
        topico.setMensaje(request.getMensaje());
        topico.setNombreCurso(request.getNombreCurso());
        topico.setAutor(autor);

        return convertirAResponse(topicoRepository.save(topico));
    }

    // ELIMINAR un tópico por ID
    public void eliminar(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException(
                    "No existe un tópico con ID: " + id);
        }
        topicoRepository.deleteById(id);
    }

    // VER un tópico específico por ID
    public TopicoResponse buscarPorId(Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un tópico con ID: " + id));
        return convertirAResponse(topico);
    }

    // EDITAR un tópico existente
    public TopicoResponse actualizar(Long id, TopicoUpdateRequest request) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un tópico con ID: " + id));

        // Solo actualizamos los campos que el usuario envió
        if (request.getTitulo() != null) {
            topico.setTitulo(request.getTitulo());
        }
        if (request.getMensaje() != null) {
            topico.setMensaje(request.getMensaje());
        }
        if (request.getNombreCurso() != null) {
            topico.setNombreCurso(request.getNombreCurso());
        }

        return convertirAResponse(topicoRepository.save(topico));
    }

    // Convierte un Topico (modelo) a TopicoResponse (DTO)
    private TopicoResponse convertirAResponse(Topico topico) {
        TopicoResponse response = new TopicoResponse();
        response.setId(topico.getId());
        response.setTitulo(topico.getTitulo());
        response.setMensaje(topico.getMensaje());
        response.setNombreCurso(topico.getNombreCurso());
        response.setFechaCreacion(topico.getFechaCreacion());
        response.setAutorNombre(topico.getAutor().getNombre());
        return response;
    }
}