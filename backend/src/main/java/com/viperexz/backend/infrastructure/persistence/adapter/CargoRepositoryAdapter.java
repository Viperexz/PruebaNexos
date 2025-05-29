package com.viperexz.backend.infrastructure.persistence.adapter;

import com.viperexz.backend.domain.model.Cargo;
import com.viperexz.backend.domain.repository.CargoRepository;
import com.viperexz.backend.infrastructure.persistence.entity.CargoEntity;
import com.viperexz.backend.infrastructure.persistence.repository.CargoJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CargoRepositoryAdapter implements CargoRepository {
    private final CargoJpaRepository jpaRepository;

    public CargoRepositoryAdapter(CargoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Cargo> findById(Long id) {
        return jpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public Cargo save(Cargo cargo) {
        CargoEntity entity = toEntity(cargo);
        CargoEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Cargo> findByNombreCargo(String nombreCargo) {
        return jpaRepository.findByNombreCargo(nombreCargo)
                .map(this::toDomain);

    }

    @Override
    public void delete(Cargo cargo) {
        CargoEntity entity = toEntity(cargo);
        jpaRepository.delete(entity);
    }

    @Override
    public List<Cargo> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    public Cargo toDomain(CargoEntity entity) {
        Cargo cargo = new Cargo();
        cargo.setIdCargo(entity.getIdCargo());
        cargo.setNombreCargo(entity.getNombreCargo());
        return cargo;
    }

    public CargoEntity toEntity(Cargo cargo) {
        CargoEntity entity = new CargoEntity();
        entity.setIdCargo(cargo.getIdCargo());
        entity.setNombreCargo(cargo.getNombreCargo());
        return entity;
    }
}