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
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
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

        // validación de unicidad en repositorio
        if (mercanciaRepository.existsByNombre(mercancia.getNombreMercancia())) {
            throw new BusinessException("Ya existe una mercancía con ese nombre.");
        }

        Mercancia guardada = mercanciaRepository.save(mercancia);
        return mapper.toResponseDTO(guardada);
    }
    @Transactional
    public MercanciaResponseDTO actualizarMercancia(MercanciaRequestDTO dto, Long idMercancia) {
        Mercancia mercancia = mercanciaRepository.findById(idMercancia)
                .orElseThrow(() -> new NotFoundException("Mercancía no encontrada"));
;
        mercanciaService.validarActualizarDatos(mercancia, dto.getNombreMercancia(), dto.getCantidadMercancia(), dto.getFechaIngresoMercancia());

        Mercancia actualizada = mercanciaRepository.save(mercancia);
        return mapper.toResponseDTO(actualizada);
    }

    @Transactional
    public boolean eliminarMercancia(Long idMercancia, Long idUsuario) {
        Mercancia mercancia = mercanciaRepository.findById(idMercancia)
                .orElseThrow(() -> new NotFoundException("Mercancía no encontrada"));

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado"));

        mercanciaService.validarEliminacion(mercancia, usuario);
        mercanciaRepository.delete(mercancia);
        return true;
    }

}
