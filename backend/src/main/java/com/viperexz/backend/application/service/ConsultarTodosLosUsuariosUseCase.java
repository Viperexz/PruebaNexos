package com.viperexz.backend.application.service;

import com.viperexz.backend.application.dto.UsuarioDTO;
import com.viperexz.backend.domain.model.Usuario;
import com.viperexz.backend.domain.repository.UsuarioRepository;
import com.viperexz.backend.interfaces.rest.mapper.UsuarioMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component

public class ConsultarTodosLosUsuariosUseCase {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public ConsultarTodosLosUsuariosUseCase(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public List<UsuarioDTO> consultarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

}
