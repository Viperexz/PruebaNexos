package com.viperexz.backend.infrastructure.persistence.adapter;

import com.viperexz.backend.domain.model.Mercancia;
import com.viperexz.backend.domain.model.Usuario;
import com.viperexz.backend.domain.repository.MercanciaRepository;
import com.viperexz.backend.infrastructure.persistence.entity.MercanciaEntity;
import com.viperexz.backend.infrastructure.persistence.entity.UsuarioEntity;
import com.viperexz.backend.infrastructure.persistence.repository.MercanciaJpaRepository;
import com.viperexz.backend.infrastructure.persistence.repository.UsuarioJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MercanciaRepositoryAdapter implements MercanciaRepository {
    private final MercanciaJpaRepository jpaRepository;


    public MercanciaRepositoryAdapter(MercanciaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Mercancia> findById(Long id) {
        return jpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<Mercancia> findAll() {
        return List.of(
                jpaRepository.findAll().stream()
                        .map(this::toDomain)
                        .toArray(Mercancia[]::new)
        );
    }

    @Override
    public Optional<Mercancia> findByNombre(String nombre) {
        return Optional.empty();
    }

    @Override
    public Mercancia save(Mercancia mercancia) {
        MercanciaEntity entity = toEntity(mercancia);
        MercanciaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return jpaRepository.existsByNombreMercancia(nombre);
    }

    @Override
    public void delete(Mercancia mercancia) {
        MercanciaEntity entity = toEntity(mercancia);
        jpaRepository.delete(entity);
    }
    private Mercancia toDomain(MercanciaEntity entity) {
        Mercancia mercancia = new Mercancia();
        mercancia.setNombreMercancia(entity.getNombreMercancia());
        mercancia.setCantidadMercancia(entity.getCantidadMercancia());
        mercancia.setFechaIngresoMercancia(entity.getFechaIngresoMercancia());
        UsuarioEntity usuarioEntity = entity.getUsuarioRegistro();
        if (usuarioEntity != null) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(usuarioEntity.getIdUsuario());
            usuario.setNombreUsuario(usuarioEntity.getNombreUsuario());
            usuario.setEdadUsuario(usuarioEntity.getEdadUsuario());
            //#TODO Registrar el cargo del usuario "Pueden aparecer mas cargos en el futuro"
            /*usuario.setCargoUsuario(usuarioEntity());*/
            usuario.setFechaIngresoUsuario(usuarioEntity.getFechaIngresoUsuario());
            mercancia.setUsuarioRegistro(usuario);
        }
        mercancia.setIdMercancia(entity.getIdMercancia());
        return mercancia;
    }

    private MercanciaEntity toEntity(Mercancia mercancia) {
        MercanciaEntity entity = new MercanciaEntity();
        entity.setNombreMercancia(mercancia.getNombreMercancia());
        entity.setCantidadMercancia(mercancia.getCantidadMercancia());
        entity.setFechaIngresoMercancia(mercancia.getFechaIngresoMercancia());
        if (mercancia.getUsuarioRegistro() != null) {
            UsuarioEntity usuarioEntity = new UsuarioEntity();
            usuarioEntity.setIdUsuario(mercancia.getUsuarioRegistro().getIdUsuario());
            usuarioEntity.setNombreUsuario(mercancia.getUsuarioRegistro().getNombreUsuario());
            usuarioEntity.setEdadUsuario(mercancia.getUsuarioRegistro().getEdadUsuario());
            //#TODO Registrar el cargo del usuario "Pueden aparecer mas cargos en el futuro"
            /*usuarioEntity.setCargo(mercancia.getUsuarioRegistro().getCargoUsuario());*/
            usuarioEntity.setFechaIngresoUsuario(mercancia.getUsuarioRegistro().getFechaIngresoUsuario());
            entity.setUsuarioRegistro(usuarioEntity);
        }
        return entity;
    }
}
