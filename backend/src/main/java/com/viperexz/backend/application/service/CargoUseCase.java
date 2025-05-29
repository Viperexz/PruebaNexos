package com.viperexz.backend.application.service;

import com.viperexz.backend.application.dto.CargoDTO;
import com.viperexz.backend.application.dto.MercanciaResponseDTO;
import com.viperexz.backend.domain.model.Cargo;
import com.viperexz.backend.domain.model.Mercancia;
import com.viperexz.backend.domain.repository.CargoRepository;
import com.viperexz.backend.domain.repository.MercanciaRepository;
import com.viperexz.backend.domain.repository.UsuarioRepository;
import com.viperexz.backend.domain.service.MercanciaService;
import com.viperexz.backend.exception.BusinessException;
import com.viperexz.backend.exception.NotFoundException;
import com.viperexz.backend.interfaces.rest.mapper.CargoMapper;
import com.viperexz.backend.interfaces.rest.mapper.MercanciaMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CargoUseCase {
    private final CargoRepository cargoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CargoMapper mapper;

    public CargoUseCase(CargoRepository cargoRepository,UsuarioRepository usuarioRepository, CargoMapper mapper) {
        this.cargoRepository = cargoRepository;
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
    }

    @Transactional
    public CargoDTO registrarCargo(CargoDTO cargoDTO) {
        if(cargoRepository.findByNombreCargo(cargoDTO.getNombreCargo()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un cargo con ese nombre");
        }
        if(cargoDTO.getNombreCargo() == null || cargoDTO.getNombreCargo().isBlank()) {
            throw new IllegalArgumentException("El nombre del cargo es obligatorio");
        }
        Cargo cargo = mapper.toDomain(cargoDTO);
        return mapper.toResponseDTO(cargoRepository.save(cargo));
    }

    @Transactional
    public List<CargoDTO> consultarTodosLosCargos() {
        return cargoRepository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CargoDTO consultarPorId(Long idCargo) {
        Cargo cargo = cargoRepository.findById(idCargo)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        return mapper.toResponseDTO(cargo);
    }

    @Transactional
    public void eliminarCargo(Long idCargo) {
        Cargo cargo = cargoRepository.findById(idCargo)
                .orElseThrow(() -> new IllegalArgumentException("Cargo no encontrado"));

        boolean hasAssociatedUsers = usuarioRepository.existsByCargoId(idCargo);
        if (hasAssociatedUsers) {
            throw new BusinessException("No se puede eliminar el cargo porque tiene usuarios asociados.");
        }

        cargoRepository.delete(cargo);
    }


    public CargoDTO consultarPorNombre(String operador) {
        Cargo cargo = cargoRepository.findByNombreCargo(operador)
                .orElseThrow(() -> new NotFoundException("Cargo no encontrado"));
        return mapper.toResponseDTO(cargo);
    }
}
