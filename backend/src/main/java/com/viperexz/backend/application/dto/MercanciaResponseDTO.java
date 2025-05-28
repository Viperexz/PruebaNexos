package com.viperexz.backend.application.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MercanciaResponseDTO {
    private Long idMercancia;
    private String nombreMercancia;
    private int cantidadMercancia;
    private LocalDate fechaIngresoMercancia;
    private String nombreUsuario;
}