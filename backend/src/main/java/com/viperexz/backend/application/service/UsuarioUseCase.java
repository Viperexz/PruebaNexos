package com.viperexz.backend.application.service;

import com.viperexz.backend.application.dto.UsuarioRequestDTO;
import com.viperexz.backend.application.dto.UsuarioResponseDTO;
import com.viperexz.backend.domain.model.Usuario;
import com.viperexz.backend.domain.repository.UsuarioRepository;
import com.viperexz.backend.exception.BusinessException;
import com.viperexz.backend.exception.NotFoundException;
import com.viperexz.backend.interfaces.rest.mapper.UsuarioMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioUseCase {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioUseCase(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Transactional
    public UsuarioResponseDTO consultarPorId(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        return usuarioMapper.toResponseDTO(usuario);
    }

    @Transactional
    public List<UsuarioResponseDTO> consultarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
   public UsuarioResponseDTO registrarUsuario(UsuarioRequestDTO dto) {
       Usuario usuario = usuarioMapper.toDomain(dto);

       // Validate user data
       if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().isBlank()) {
           throw new BusinessException("El nombre del usuario es obligatorio.");
       }

       if (usuarioRepository.existsByNombreUsuario(usuario.getNombreUsuario())) {
           throw new BusinessException("Ya existe un usuario con ese nombre.");
       }

       Usuario guardado = usuarioRepository.save(usuario);
       return usuarioMapper.toResponseDTO(guardado);
   }

    @Transactional
   public UsuarioResponseDTO actualizarUsuario(UsuarioResponseDTO dto, Long idUsuario) {
       Usuario usuario = usuarioRepository.findById(idUsuario)
               .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

       // Update user data
       usuario.setNombreUsuario(dto.getNombreUsuario());
       usuario.setEdadUsuario(dto.getEdadUsuario());
       usuario.setCargoUsuario(dto.getCargoUsuario());
       usuario.setFechaIngresoUsuario(dto.getFechaIngresoUsuario());

       Usuario actualizado = usuarioRepository.save(usuario);
       return usuarioMapper.toResponseDTO(actualizado);
   }

    @Transactional
   public boolean eliminarUsuario(Long idUsuario) {
       Usuario usuario = usuarioRepository.findById(idUsuario)
               .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
       usuarioRepository.deleteByUsuario(usuario);
       return true;
   }


}
