package com.viperexz.backend.application.service;

import com.viperexz.backend.application.dto.MercanciaResponseDTO;
import com.viperexz.backend.domain.model.Mercancia;
import com.viperexz.backend.domain.repository.MercanciaRepository;
import com.viperexz.backend.interfaces.rest.mapper.MercanciaMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConsultarTodasLasMercanciasUseCase {

    private final MercanciaRepository mercanciaRepository;
    private final MercanciaMapper mapper;

    public ConsultarTodasLasMercanciasUseCase(MercanciaRepository mercanciaRepository,
                                              MercanciaMapper mapper) {
        this.mercanciaRepository = mercanciaRepository;
        this.mapper = mapper;
    }

    public List<MercanciaResponseDTO> consultarTodos() {
        List<Mercancia> mercancia = mercanciaRepository.findAll();
        return mercancia.stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
