package com.viperexz.backend.infrastructure.persistence.adapter;

import com.viperexz.backend.domain.model.Cargo;
import com.viperexz.backend.domain.model.Mercancia;
import com.viperexz.backend.domain.model.Usuario;
import com.viperexz.backend.domain.repository.MercanciaRepository;
import com.viperexz.backend.infrastructure.persistence.entity.CargoEntity;
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
    private final CargoRepositoryAdapter cargoRepositoryAdapter;


    public MercanciaRepositoryAdapter(MercanciaJpaRepository jpaRepository, CargoRepositoryAdapter cargoRepositoryAdapter) {
        this.jpaRepository = jpaRepository;
        this.cargoRepositoryAdapter = cargoRepositoryAdapter;
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
        jpaRepository.deleteById(mercancia.getIdMercancia());
    }

    @Override
    public boolean existsByUsuarioId(Long idUsuario) {
        return jpaRepository.existsByUsuarioRegistroIdUsuario(idUsuario);

    }


    public Mercancia toDomain(MercanciaEntity entity) {
        Mercancia mercancia = new Mercancia();
        mercancia.setIdMercancia(entity.getIdMercancia());
        mercancia.setNombreMercancia(entity.getNombreMercancia());
        mercancia.setCantidadMercancia(entity.getCantidadMercancia());
        mercancia.setFechaIngresoMercancia(entity.getFechaIngresoMercancia());
        UsuarioEntity usuarioEntity = entity.getUsuarioRegistro();
        if (usuarioEntity != null) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(usuarioEntity.getIdUsuario());
            usuario.setNombreUsuario(usuarioEntity.getNombreUsuario());
            mercancia.setUsuarioRegistro(usuario);
            CargoEntity cargoEntity = usuarioEntity.getCargoUsuario();
            if (cargoEntity != null) {
                Cargo cargo = new Cargo();
                cargo.setIdCargo(cargoEntity.getIdCargo());
                cargo.setNombreCargo(cargoEntity.getNombreCargo());
                usuario.setCargoUsuario(cargo);
            }
        }
        UsuarioEntity usuarioModificacionEntity = entity.getUsuarioModificacion();
        if(usuarioModificacionEntity != null) {
            Usuario usuarioModificacion = new Usuario();
            usuarioModificacion.setIdUsuario(usuarioModificacionEntity.getIdUsuario());
            usuarioModificacion.setNombreUsuario(usuarioModificacionEntity.getNombreUsuario());
            CargoEntity cargoEntity = usuarioEntity.getCargoUsuario();
            if (cargoEntity != null) {
                Cargo cargo = new Cargo();
                cargo.setIdCargo(cargoEntity.getIdCargo());
                cargo.setNombreCargo(cargoEntity.getNombreCargo());
                usuarioModificacion.setCargoUsuario(cargo);
            }
            mercancia.setUsuarioModificacion(usuarioModificacion);
        }
        mercancia.setFechaModificacion(entity.getFechaModificacion());

        return mercancia;
    }

    public MercanciaEntity toEntity(Mercancia mercancia) {
        MercanciaEntity entity = new MercanciaEntity();
        entity.setIdMercancia(mercancia.getIdMercancia()); // ✔ IMPORTANTE
        entity.setNombreMercancia(mercancia.getNombreMercancia());
        entity.setCantidadMercancia(mercancia.getCantidadMercancia());
        entity.setFechaIngresoMercancia(mercancia.getFechaIngresoMercancia());

        if (mercancia.getUsuarioRegistro() != null) {
            UsuarioEntity usuarioEntity = new UsuarioEntity();
            usuarioEntity.setIdUsuario(mercancia.getUsuarioRegistro().getIdUsuario()); // Solo ID
            usuarioEntity.setNombreUsuario(mercancia.getUsuarioRegistro().getNombreUsuario());
            entity.setUsuarioRegistro(usuarioEntity);
        }

        if (mercancia.getUsuarioModificacion() != null) {
            UsuarioEntity usuarioEntity = new UsuarioEntity();
            usuarioEntity.setIdUsuario(mercancia.getUsuarioModificacion().getIdUsuario()); // Solo ID
            usuarioEntity.setNombreUsuario(mercancia.getUsuarioModificacion().getNombreUsuario());
            entity.setUsuarioModificacion(usuarioEntity);
        }

        entity.setFechaModificacion(mercancia.getFechaModificacion());
        return entity;
    }

}
