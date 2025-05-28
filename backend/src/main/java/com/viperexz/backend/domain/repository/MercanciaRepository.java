package com.viperexz.backend.domain.repository;

import com.viperexz.backend.domain.model.Mercancia;
import com.viperexz.backend.infrastructure.persistence.entity.MercanciaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MercanciaRepository {
    Optional<Mercancia> findById(Long id);
    List<Mercancia> findAll();
    Mercancia save(Mercancia mercancia);
    boolean existsByNombre(String nombre);
    void delete(Mercancia mercancia);
}
