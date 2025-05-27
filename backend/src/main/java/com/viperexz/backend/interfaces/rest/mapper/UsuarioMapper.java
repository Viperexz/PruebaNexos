package com.viperexz.backend.interfaces.rest.mapper;

import com.viperexz.backend.application.dto.UsuarioDTO;
import com.viperexz.backend.domain.model.Mercancia;
import com.viperexz.backend.domain.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {


    public UsuarioDTO toResponseDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setEdadUsuario(usuario.getEdadUsuario());
        dto.setCargoUsuario(usuario.getCargoUsuario());
        dto.setFechaIngresoUsuario(usuario.getFechaIngresoUsuario());
        return dto;
    }
}
