package com.viperexz.backend.domain.service;

import com.viperexz.backend.domain.model.Mercancia;
import com.viperexz.backend.domain.model.Usuario;
import com.viperexz.backend.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MercanciaService {

    public void validarRegistro(Mercancia mercancia) {
        if (mercancia.getCantidadMercancia() <= 0) {
            throw new BusinessException("La cantidad debe ser mayor que cero.");
        }

        if (mercancia.getFechaIngresoMercancia().isAfter(LocalDate.now())) {
            throw new BusinessException("La fecha de ingreso no puede ser futura.");
        }

        if (mercancia.getNombreMercancia() == null || mercancia.getNombreMercancia().isBlank()) {
            throw new BusinessException("El nombre es obligatorio.");
        }
        if(mercancia.getCantidadMercancia()>1000000)
        {
            throw new BusinessException("La cantidad de mercancía no puede ser mayor que 1.000.000");
        }
    }

    public void validarEliminacion(Long idUsuarioMercancia, Long idUsuarioSolicitante) {
        if (!idUsuarioMercancia.equals(idUsuarioSolicitante)) {
            throw new BusinessException("Solo el usuario que registró la mercancía puede eliminarla.");
        }
    }

    public void validarActualizarDatos(Mercancia mercancia, String nuevoNombre, int nuevaCantidad, LocalDate nuevaFecha) {
        if( mercancia == null) {
            throw new BusinessException("La mercancía no puede ser nula.");
        }
        if (nuevoNombre == null || nuevoNombre.isBlank()) {
            throw new BusinessException("El nombre es obligatorio.");
        }
        if (nuevaCantidad <= 0) {
            throw new BusinessException("La cantidad debe ser mayor que cero.");
        }

        if (nuevaFecha.isAfter(LocalDate.now())) {
            throw new BusinessException("La fecha de ingreso no puede ser futura.");
        }

    }

}