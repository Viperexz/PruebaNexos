package com.viperexz.backend.interfaces.rest.mapper;

import com.viperexz.backend.application.dto.UsuarioRequestDTO;
import com.viperexz.backend.application.dto.UsuarioResponseDTO;
import com.viperexz.backend.domain.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {


    public UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setEdadUsuario(usuario.getEdadUsuario());
        dto.setCargoUsuario(usuario.getCargoUsuario());
        dto.setFechaIngresoUsuario(usuario.getFechaIngresoUsuario());
        return dto;
    }

    public Usuario toDomain(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setEdadUsuario(dto.getEdadUsuario());
        usuario.setCargoUsuario(dto.getCargoUsuario());
        usuario.setFechaIngresoUsuario(dto.getFechaIngresoUsuario());
        return usuario;
    }
}
