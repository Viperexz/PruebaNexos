package com.viperexz.backend.interfaces.rest.mapper;

import com.viperexz.backend.application.dto.MercanciaResponseDTO;
import com.viperexz.backend.application.dto.UsuarioRequestDTO;
import com.viperexz.backend.application.dto.UsuarioResponseDTO;
import com.viperexz.backend.application.service.CargoUseCase;
import com.viperexz.backend.domain.model.Cargo;
import com.viperexz.backend.domain.model.Usuario;
import com.viperexz.backend.domain.repository.CargoRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {


    private final CargoRepository cargoRepository;
    private final CargoUseCase cargoUseCase;

    public UsuarioMapper(CargoRepository cargoRepository, CargoUseCase cargoUseCase) {
        this.cargoRepository = cargoRepository;
        this.cargoUseCase = cargoUseCase;
    }

    public UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setEdadUsuario(usuario.getEdadUsuario());
        dto.setCargoUsuario(usuario.getCargoUsuario().getNombreCargo());
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
                    mDto.setNombreUsuarioRegistro(usuario.getNombreUsuario());
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
        Cargo cargo = cargoRepository.findById(dto.getIdCargoUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Cargo no encontrado"));
        usuario.setCargoUsuario(cargo);
        usuario.setFechaIngresoUsuario(dto.getFechaIngresoUsuario());
        return usuario;
    }
}
