package com.viperexz.backend.application.service;

import com.viperexz.backend.application.dto.UsuarioRequestDTO;
import com.viperexz.backend.application.dto.UsuarioResponseDTO;
import com.viperexz.backend.domain.model.Usuario;
import com.viperexz.backend.domain.repository.CargoRepository;
import com.viperexz.backend.domain.repository.MercanciaRepository;
import com.viperexz.backend.domain.repository.UsuarioRepository;
import com.viperexz.backend.domain.service.UsuarioService;
import com.viperexz.backend.exception.BusinessException;
import com.viperexz.backend.exception.NotFoundException;
import com.viperexz.backend.interfaces.rest.mapper.CargoMapper;
import com.viperexz.backend.interfaces.rest.mapper.UsuarioMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioUseCase {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final MercanciaRepository mercanciaRepository;
    private final CargoRepository cargoRepository;
    private final UsuarioService usuarioService;

    public UsuarioUseCase(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper, MercanciaRepository mercanciaRepository, CargoRepository cargoRepository , UsuarioService usuarioService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.mercanciaRepository = mercanciaRepository;
        this.cargoRepository = cargoRepository;
        this.usuarioService = usuarioService;
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
       if (usuarioRepository.existsByNombreUsuario(usuario.getNombreUsuario())) {
           throw new BusinessException("Ya existe un usuario con ese nombre.");
       }
        usuarioService.validarRegistro(usuario);

       Usuario guardado = usuarioRepository.save(usuario);
       return usuarioMapper.toResponseDTO(guardado);
   }
@Transactional
public UsuarioResponseDTO actualizarUsuario(UsuarioRequestDTO dto, Long idUsuario) {
    Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

    // Update user data
    usuario.setNombreUsuario(dto.getNombreUsuario());
    usuario.setEdadUsuario(dto.getEdadUsuario());
    usuario.setFechaIngresoUsuario(dto.getFechaIngresoUsuario());
    usuario.setCargoUsuario(cargoRepository.findById(dto.getIdCargoUsuario())
            .orElseThrow(() -> new NotFoundException("Cargo no encontrado")));

    // Verificar si el nombre ya existe en otro usuario
    if (!usuario.getNombreUsuario().equals(dto.getNombreUsuario()) &&
            usuarioRepository.existsByNombreUsuario(dto.getNombreUsuario())) {
        throw new BusinessException("Ya existe un usuario con ese nombre.");
    }

    Usuario actualizado = usuarioRepository.save(usuario);
    return usuarioMapper.toResponseDTO(actualizado);
}

    @Transactional
   public boolean eliminarUsuario(Long idUsuario) {
       Usuario usuario = usuarioRepository.findById(idUsuario)
               .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        if (mercanciaRepository.existsByUsuarioId(idUsuario)) {
            throw new BusinessException("No se puede eliminar el usuario porque tiene mercancías registradas.");
        }

       usuarioRepository.deleteByUsuario(usuario);
       return true;
   }

    public UsuarioResponseDTO consultarPorNombre(String usuarioConsulta) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(usuarioConsulta)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        return usuarioMapper.toResponseDTO(usuario);
    }
}
