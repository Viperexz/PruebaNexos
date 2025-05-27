package com.viperexz.backend.application.service;

import com.viperexz.backend.application.dto.MercanciaRequestDTO;
import com.viperexz.backend.application.dto.MercanciaResponseDTO;
import com.viperexz.backend.domain.model.Mercancia;
import com.viperexz.backend.domain.model.Usuario;
import com.viperexz.backend.domain.repository.MercanciaRepository;
import com.viperexz.backend.domain.repository.UsuarioRepository;
import com.viperexz.backend.domain.service.MercanciaService;
import com.viperexz.backend.exception.NotFoundException;
import com.viperexz.backend.interfaces.rest.mapper.MercanciaMapper;
import org.springframework.stereotype.Component;


@Component
public class EditarMercanciaUseCase {


    private final MercanciaRepository mercanciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final MercanciaMapper mapper;
    private final MercanciaService mercanciaService;

    public EditarMercanciaUseCase(MercanciaRepository mercanciaRepository,
                            UsuarioRepository usuarioRepository,
                            MercanciaMapper mapper,
                            MercanciaService mercanciaService) {
        this.mercanciaRepository = mercanciaRepository;
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
        this.mercanciaService = mercanciaService;
    }

    public MercanciaResponseDTO editar(MercanciaRequestDTO dto){
        Mercancia mercancia = mercanciaRepository.findByNombre(dto.getNombreMercancia())
                .orElseThrow(() -> new NotFoundException("Mercancía no encontrada"));

        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        mercanciaService.validarEliminacion(mercancia, usuario);

        mercanciaService.validarActualizarDatos(mercancia, dto.getNombreMercancia(), dto.getCantidadMercancia(), dto.getFechaIngresoMercancia());

        Mercancia actualizada = mercanciaRepository.save(mercancia);
        return mapper.toResponseDTO(actualizada);
    }
}
