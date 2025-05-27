package com.viperexz.backend.application.service;

import com.viperexz.backend.domain.model.Mercancia;
import com.viperexz.backend.domain.model.Usuario;
import com.viperexz.backend.domain.repository.MercanciaRepository;
import com.viperexz.backend.domain.repository.UsuarioRepository;
import com.viperexz.backend.domain.service.MercanciaService;
import com.viperexz.backend.exception.NotFoundException;
import com.viperexz.backend.interfaces.rest.mapper.MercanciaMapper;
import org.springframework.stereotype.Component;

@Component
public class EliminarMercanciaUseCase {

    private final MercanciaRepository mercanciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final MercanciaService mercanciaService;

    public EliminarMercanciaUseCase(MercanciaRepository mercanciaRepository,
                                  UsuarioRepository usuarioRepository,
                                  MercanciaService mercanciaService) {
        this.mercanciaRepository = mercanciaRepository;
        this.usuarioRepository = usuarioRepository;
        this.mercanciaService = mercanciaService;
    }

    public boolean eliminar (Long idMercancia, Usuario usuarioSolicitante) {
        Mercancia mercancia = mercanciaRepository.findById(idMercancia)
                .orElseThrow(() -> new NotFoundException("Mercancía no encontrada"));
        Usuario usuario = usuarioRepository.findById(usuarioSolicitante.getIdUsuario())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        mercanciaService.validarEliminacion(mercancia, usuario);
        mercanciaRepository.delete(mercancia);
        return true;
    }

}
