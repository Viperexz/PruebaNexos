package com.viperexz.backend.domain.repository;

import com.viperexz.backend.domain.model.Cargo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CargoRepository {
    Optional<Cargo> findById(Long id);
    List<Cargo> findAll();
    Cargo save(Cargo cargo);
    Optional<Cargo> findByNombreCargo(String nombreCargo);
    void delete(Cargo cargo);


}
