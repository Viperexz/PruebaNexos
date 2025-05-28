package com.viperexz.backend.interfaces.rest.mapper;

import com.viperexz.backend.application.dto.MercanciaRequestDTO;
import com.viperexz.backend.application.dto.MercanciaResponseDTO;
import com.viperexz.backend.domain.model.Mercancia;
import com.viperexz.backend.domain.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class MercanciaMapper {

    public Mercancia toDomain(MercanciaRequestDTO dto, Usuario usuario) {
        return  Mercancia.builder()
                .nombreMercancia(dto.getNombreMercancia())
                .cantidadMercancia(dto.getCantidadMercancia())
                .fechaIngresoMercancia(dto.getFechaIngresoMercancia())
                .usuarioRegistro(usuario)
                .build();
    }

    public MercanciaResponseDTO toResponseDTO(Mercancia mercancia) {
        MercanciaResponseDTO dto = new MercanciaResponseDTO();
        dto.setIdMercancia(mercancia.getIdMercancia());
        dto.setNombreMercancia(mercancia.getNombreMercancia());
        dto.setCantidadMercancia(mercancia.getCantidadMercancia());
        dto.setFechaIngresoMercancia(mercancia.getFechaIngresoMercancia());
        dto.setNombreUsuario(mercancia.getUsuarioRegistro().getNombreUsuario());
        return dto;
    }
}
