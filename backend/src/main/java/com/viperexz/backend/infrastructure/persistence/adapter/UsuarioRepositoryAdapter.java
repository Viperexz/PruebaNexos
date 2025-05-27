package com.viperexz.backend.infrastructure.persistence.adapter;

import com.viperexz.backend.domain.model.Usuario;
import com.viperexz.backend.domain.repository.UsuarioRepository;
import com.viperexz.backend.infrastructure.persistence.entity.UsuarioEntity;
import com.viperexz.backend.infrastructure.persistence.repository.UsuarioJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UsuarioRepositoryAdapter implements UsuarioRepository {

    private final UsuarioJpaRepository jpaRepository;

    public UsuarioRepositoryAdapter(UsuarioJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return jpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity entity = toEntity(usuario);
        UsuarioEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public List<Usuario> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    private Usuario toDomain(UsuarioEntity entity) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(entity.getIdUsuario());
        usuario.setNombreUsuario(entity.getNombreUsuario());
        usuario.setEdadUsuario(entity.getEdadUsuario());
        //#TODO Registrar el cargo del usuario "Pueden aparecer mas cargos en el futuro"
        /*usuario.setCargoUsuario(entity.getCargo());*/
        usuario.setFechaIngresoUsuario(entity.getFechaIngresoUsuario());
        return usuario;
    }

    private UsuarioEntity toEntity(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setIdUsuario(usuario.getIdUsuario());
        entity.setNombreUsuario(usuario.getNombreUsuario());
        entity.setEdadUsuario(usuario.getEdadUsuario());
        //#TODO Registrar el cargo del usuario "Pueden aparecer mas cargos en el futuro"
        /*entity.setCargo(usuario.getCargoUsuario());*/
        entity.setFechaIngresoUsuario(usuario.getFechaIngresoUsuario());
        return entity;
    }
}
