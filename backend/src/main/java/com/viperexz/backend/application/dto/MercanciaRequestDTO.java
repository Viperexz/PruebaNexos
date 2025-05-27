package com.viperexz.backend.application.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MercanciaRequestDTO {
    private Long idUsuario;
    private String nombreMercancia;
    private int cantidadMercancia;
    private LocalDate fechaIngresoMercancia;
}
