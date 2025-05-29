package com.viperexz.backend.domain.service;

import com.viperexz.backend.domain.model.Mercancia;
import com.viperexz.backend.domain.model.Usuario;
import com.viperexz.backend.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UsuarioService {
    public void validarRegistro(Usuario usuario) {
        if (usuario.getEdadUsuario() <= 0) {
            throw new BusinessException("La edad debe ser mayor que cero.");
        }

        if (usuario.getEdadUsuario() > 100) {
            throw new BusinessException("Ya es hora de jubilarse");
        }

        if (usuario.getFechaIngresoUsuario().isAfter(LocalDate.now())) {
            throw new BusinessException("La fecha de ingreso no puede ser futura.");
        }

        if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().isBlank()) {
            throw new BusinessException("El nombre es obligatorio.");
        }
    }
}
