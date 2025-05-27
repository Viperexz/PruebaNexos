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
import org.springframework.stereotype.Component;

@Component
public class CrearMercanciaUseCase {

    private final MercanciaRepository mercanciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final MercanciaMapper mapper;
    private final MercanciaService mercanciaService;

    public CrearMercanciaUseCase(MercanciaRepository mercanciaRepository,
                            UsuarioRepository usuarioRepository,
                            MercanciaMapper mapper,
                            MercanciaService mercanciaService) {
        this.mercanciaRepository = mercanciaRepository;
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
        this.mercanciaService = mercanciaService;
    }

    public MercanciaResponseDTO registrar(MercanciaRequestDTO dto) {
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
}
