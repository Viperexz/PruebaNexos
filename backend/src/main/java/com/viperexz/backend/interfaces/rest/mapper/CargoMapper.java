package com.viperexz.backend.interfaces.rest.mapper;

import com.viperexz.backend.application.dto.CargoDTO;
import com.viperexz.backend.domain.model.Cargo;
import org.springframework.stereotype.Component;

@Component
public class CargoMapper {

    public Cargo toDomain(CargoDTO dto) {
        return  Cargo.builder()
                .nombreCargo(dto.getNombreCargo())
                .build();
    }

    public CargoDTO toResponseDTO(Cargo cargo) {
        CargoDTO dto = new CargoDTO();
        dto.setIdCargo(cargo.getIdCargo());
        dto.setNombreCargo(cargo.getNombreCargo());
        return dto;
    }
}
