package com.viperexz.backend.infrastructure.persistence.repository;

import com.viperexz.backend.domain.model.Cargo;
import com.viperexz.backend.infrastructure.persistence.entity.CargoEntity;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CargoJpaRepository extends JpaRepository<CargoEntity, Long> {
    boolean existsByNombreCargo(String nombreCargo);
    Optional<CargoEntity> findByNombreCargo(String nombreCargo);
}
