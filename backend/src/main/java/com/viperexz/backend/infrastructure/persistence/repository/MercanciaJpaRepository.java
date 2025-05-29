package com.viperexz.backend.infrastructure.persistence.repository;

import com.viperexz.backend.infrastructure.persistence.entity.MercanciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MercanciaJpaRepository extends JpaRepository<MercanciaEntity, Long> {
    boolean existsByNombreMercancia(String nombreMercancia);
}
