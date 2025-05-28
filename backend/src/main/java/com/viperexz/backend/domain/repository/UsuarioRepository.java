package com.viperexz.backend.domain.repository;

import com.viperexz.backend.domain.model.Usuario;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository {
    Optional<Usuario> findById(Long id);
    Usuario save(Usuario usuario);
    List<Usuario> findAll();
    boolean existsByNombreUsuario(String nombreUsuario);
    void deleteByUsuario(Usuario usuario);
}
