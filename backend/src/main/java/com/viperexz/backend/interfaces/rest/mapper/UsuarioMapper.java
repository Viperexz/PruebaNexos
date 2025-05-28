package com.viperexz.backend.interfaces.rest.mapper;

import com.viperexz.backend.application.dto.MercanciaResponseDTO;
import com.viperexz.backend.application.dto.UsuarioRequestDTO;
import com.viperexz.backend.application.dto.UsuarioResponseDTO;
import com.viperexz.backend.domain.model.Usuario;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {


    public UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setEdadUsuario(usuario.getEdadUsuario());
        dto.setCargoUsuario(usuario.getCargoUsuario());
        dto.setFechaIngresoUsuario(usuario.getFechaIngresoUsuario());

        if(usuario.getMercanciasUsuario() == null || usuario.getMercanciasUsuario().isEmpty()) {
            dto.setMercanciasUsuario(List.of());
            return dto;
        }
        Hibernate.initialize(usuario.getMercanciasUsuario());
        List<MercanciaResponseDTO> mercanciasDTO = usuario.getMercanciasUsuario()
                .stream()
                .map(mercancia -> {
                    MercanciaResponseDTO mDto = new MercanciaResponseDTO();
                    mDto.setNombreMercancia(mercancia.getNombreMercancia());
                    mDto.setCantidadMercancia(mercancia.getCantidadMercancia());
                    mDto.setFechaIngresoMercancia(mercancia.getFechaIngresoMercancia());
                    mDto.setNombreUsuario(usuario.getNombreUsuario());
                    return mDto;
                })
                .collect(Collectors.toList());
        dto.setMercanciasUsuario(mercanciasDTO);
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
