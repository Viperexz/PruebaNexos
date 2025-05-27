package com.viperexz.backend.application.service;

import com.viperexz.backend.application.dto.UsuarioDTO;
import com.viperexz.backend.domain.model.Usuario;
import com.viperexz.backend.domain.repository.UsuarioRepository;
import com.viperexz.backend.exception.NotFoundException;
import com.viperexz.backend.interfaces.rest.mapper.UsuarioMapper;
import org.springframework.stereotype.Component;

@Component
public class ConsultarUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public ConsultarUsuarioUseCase(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioDTO consultarPorId(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        return usuarioMapper.toResponseDTO(usuario);
    }

}
