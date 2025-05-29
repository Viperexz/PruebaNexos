package com.viperexz.backend.infrastructure.persistence.adapter;

import com.viperexz.backend.domain.model.Mercancia;
import com.viperexz.backend.domain.model.Usuario;
import com.viperexz.backend.domain.repository.UsuarioRepository;
import com.viperexz.backend.infrastructure.persistence.entity.MercanciaEntity;
import com.viperexz.backend.infrastructure.persistence.entity.UsuarioEntity;
import com.viperexz.backend.infrastructure.persistence.repository.UsuarioJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UsuarioRepositoryAdapter implements UsuarioRepository {

    private final UsuarioJpaRepository jpaRepository;
    private final MercanciaRepositoryAdapter mercanciaRepositoryAdapter;
    private final CargoRepositoryAdapter cargoRepositoryAdapter;

    public UsuarioRepositoryAdapter(UsuarioJpaRepository jpaRepository, MercanciaRepositoryAdapter mercanciaRepositoryAdapter,
                                    CargoRepositoryAdapter cargoRepositoryAdapter) {
        this.jpaRepository = jpaRepository;
        this.mercanciaRepositoryAdapter = mercanciaRepositoryAdapter;
        this.cargoRepositoryAdapter = cargoRepositoryAdapter;
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

    @Override
    public boolean existsByNombreUsuario(String nombreUsuario) {
        return jpaRepository.existsByNombreUsuario(nombreUsuario);
    }

    @Override
    public void deleteByUsuario(Usuario usuario) {
        UsuarioEntity entity = toEntity(usuario);
        jpaRepository.delete(entity);
    }

    private Usuario toDomain(UsuarioEntity entity) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(entity.getIdUsuario());
        usuario.setNombreUsuario(entity.getNombreUsuario());
        usuario.setEdadUsuario(entity.getEdadUsuario());
        usuario.setCargoUsuario(cargoRepositoryAdapter.toDomain(entity.getCargoUsuario()));
        usuario.setFechaIngresoUsuario(entity.getFechaIngresoUsuario());
        return usuario;
    }

    private UsuarioEntity toEntity(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setIdUsuario(usuario.getIdUsuario());
        entity.setNombreUsuario(usuario.getNombreUsuario());
        entity.setEdadUsuario(usuario.getEdadUsuario());
        entity.setCargoUsuario(cargoRepositoryAdapter.toEntity(usuario.getCargoUsuario()));
        entity.setFechaIngresoUsuario(usuario.getFechaIngresoUsuario());
        return entity;
    }
}
