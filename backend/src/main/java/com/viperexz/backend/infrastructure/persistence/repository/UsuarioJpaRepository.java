package com.viperexz.backend.infrastructure.persistence.repository;

import com.viperexz.backend.infrastructure.persistence.entity.UsuarioEntity;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {
    boolean existsByNombreUsuario(String nombreUsuario);
}
