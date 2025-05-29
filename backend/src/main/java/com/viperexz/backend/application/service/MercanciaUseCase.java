package com.viperexz.backend.application.service;

import com.viperexz.backend.application.dto.MercanciaRequestDTO;
import com.viperexz.backend.application.dto.MercanciaResponseDTO;
import com.viperexz.backend.domain.model.Mercancia;
import com.viperexz.backend.domain.model.Usuario;
import com.viperexz.backend.domain.repository.MercanciaRepository;
import com.viperexz.backend.domain.repository.UsuarioRepository;
import com.viperexz.backend.domain.service.MercanciaService;
import com.viperexz.backend.exception.BusinessException;
import com.viperexz.backend.exception.NotFoundException;
import com.viperexz.backend.interfaces.rest.mapper.MercanciaMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MercanciaUseCase {

    private final MercanciaRepository mercanciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final MercanciaMapper mapper;
    private final MercanciaService mercanciaService;

    public MercanciaUseCase(MercanciaRepository mercanciaRepository,
                                 UsuarioRepository usuarioRepository,
                                 MercanciaMapper mapper,
                                 MercanciaService mercanciaService) {
        this.mercanciaRepository = mercanciaRepository;
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
        this.mercanciaService = mercanciaService;
    }

    @Transactional
    public MercanciaResponseDTO consultarPorId(Long idUsuario) {
        Mercancia mercancia = mercanciaRepository.findById(idUsuario)
                .orElseThrow(() -> new NotFoundException("Mercancia no encontrado"));
        return mapper.toResponseDTO(mercancia);
    }
    @Transactional
    public List<MercanciaResponseDTO> consultarTodos() {
        List<Mercancia> mercancia = mercanciaRepository.findAll();
        return mercancia.stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public MercanciaResponseDTO registrarMercancia(MercanciaRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        Mercancia mercancia = mapper.toDomain(dto, usuario);

        mercanciaService.validarRegistro(mercancia);

        if (mercanciaRepository.existsByNombre(mercancia.getNombreMercancia())) {
            throw new BusinessException("Ya existe una mercancía con ese nombre.");
        }

        Mercancia guardada = mercanciaRepository.save(mercancia);
        return mapper.toResponseDTO(guardada);
    }

    @Transactional
    public MercanciaResponseDTO actualizarMercancia(MercanciaRequestDTO dto, Long idMercancia, Long idUsuario) {
        Mercancia mercancia = mercanciaRepository.findById(idMercancia)
                .orElseThrow(() -> new NotFoundException("Mercancía no encontrada"));

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        // Verificar si el nombre ya existe en otra mercancía
        if (!mercancia.getNombreMercancia().equals(dto.getNombreMercancia()) &&
                mercanciaRepository.existsByNombre(dto.getNombreMercancia())) {
            throw new BusinessException("Ya existe una mercancía con ese nombre.");
        }

        mercanciaService.validarActualizarDatos(mercancia, dto.getNombreMercancia(), dto.getCantidadMercancia(), dto.getFechaIngresoMercancia());
        mercancia.setUsuarioModificacion(usuario);
        mercancia.setNombreMercancia(dto.getNombreMercancia());
        mercancia.setCantidadMercancia(dto.getCantidadMercancia());
        mercancia.setFechaModificacion(LocalDate.now());

        Mercancia actualizada = mercanciaRepository.save(mercancia);

        return mapper.toResponseDTO(actualizada);
    }

    @Transactional
    public void eliminarMercancia(Long idMercancia, Long idUsuario) {
        Mercancia mercancia = mercanciaRepository.findById(idMercancia)
                .orElseThrow(() -> new NotFoundException("Mercancía no encontrada"));
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado"));

        mercanciaService.validarEliminacion(mercancia.getUsuarioRegistro().getIdUsuario(), usuario.getIdUsuario());
        mercanciaRepository.delete(mercancia);
    }

    public List<MercanciaResponseDTO> filtrarMercancia(
            String nombre,
            LocalDate fechaRegistroDesde,
            LocalDate fechaRegistroHasta,
            Long idUsuarioRegistro,
            LocalDate fechaModificacionDesde,
            LocalDate fechaModificacionHasta,
            Long idUsuarioModificacion
    ) {
        if (nombre == null && fechaRegistroDesde == null && fechaRegistroHasta == null &&
                idUsuarioRegistro == null && fechaModificacionDesde == null &&
                fechaModificacionHasta == null && idUsuarioModificacion == null) {
            throw new BusinessException("Debe proporcionar al menos un filtro.");
        }

        List<Mercancia> mercancias = mercanciaRepository.findAll();

        if (nombre != null && !nombre.isBlank()) {
            mercancias = mercancias.stream()
                    .filter(m -> m.getNombreMercancia().equalsIgnoreCase(nombre))
                    .collect(Collectors.toList());
        }

        if (fechaRegistroDesde != null) {
            mercancias = mercancias.stream()
                    .filter(m -> m.getFechaIngresoMercancia() != null &&
                            !m.getFechaIngresoMercancia().isBefore(fechaRegistroDesde))
                    .collect(Collectors.toList());
        }

        if (fechaRegistroHasta != null) {
            mercancias = mercancias.stream()
                    .filter(m -> m.getFechaIngresoMercancia() != null &&
                            !m.getFechaIngresoMercancia().isAfter(fechaRegistroHasta))
                    .collect(Collectors.toList());
        }

        if (idUsuarioRegistro != null) {
            mercancias = mercancias.stream()
                    .filter(m -> m.getUsuarioRegistro() != null &&
                            m.getUsuarioRegistro().getIdUsuario().equals(idUsuarioRegistro))
                    .collect(Collectors.toList());
        }

        if (fechaModificacionDesde != null) {
            mercancias = mercancias.stream()
                    .filter(m -> m.getFechaModificacion() != null &&
                            !m.getFechaModificacion().isBefore(fechaModificacionDesde))
                    .collect(Collectors.toList());
        }

        if (fechaModificacionHasta != null) {
            mercancias = mercancias.stream()
                    .filter(m -> m.getFechaModificacion() != null &&
                            !m.getFechaModificacion().isAfter(fechaModificacionHasta))
                    .collect(Collectors.toList());
        }

        if (idUsuarioModificacion != null) {
            mercancias = mercancias.stream()
                    .filter(m -> m.getUsuarioModificacion() != null &&
                            m.getUsuarioModificacion().getIdUsuario().equals(idUsuarioModificacion))
                    .collect(Collectors.toList());
        }

        if (mercancias.isEmpty()) {
            throw new NotFoundException("No se encontraron mercancías con los criterios especificados.");
        }

        return mercancias.stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }




}
