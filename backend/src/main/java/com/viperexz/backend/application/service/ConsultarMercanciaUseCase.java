package com.viperexz.backend.application.service;

import com.viperexz.backend.application.dto.MercanciaResponseDTO;
import com.viperexz.backend.domain.model.Mercancia;
import com.viperexz.backend.domain.repository.MercanciaRepository;
import com.viperexz.backend.exception.NotFoundException;
import com.viperexz.backend.interfaces.rest.mapper.MercanciaMapper;
import org.springframework.stereotype.Component;

@Component
public class ConsultarMercanciaUseCase {

    private final MercanciaRepository mercanciaRepository;
    private final MercanciaMapper mapper;

    public ConsultarMercanciaUseCase(MercanciaRepository mercanciaRepository,
                                             MercanciaMapper mapper) {
        this.mercanciaRepository = mercanciaRepository;
        this.mapper = mapper;
    }

    public MercanciaResponseDTO consultarPorId(Long idUsuario) {
        Mercancia mercancia = mercanciaRepository.findById(idUsuario)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        return mapper.toResponseDTO(mercancia);
    }
}
