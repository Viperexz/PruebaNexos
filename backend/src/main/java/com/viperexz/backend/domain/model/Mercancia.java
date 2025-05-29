package com.viperexz.backend.domain.model;

import lombok.*;

import java.time.LocalDate;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Mercancia {

    private Long idMercancia;
    private String nombreMercancia;
    private int cantidadMercancia;
    private LocalDate fechaIngresoMercancia;
    private Usuario usuarioRegistro;
    private Usuario usuarioModificacion;
    private LocalDate fechaModificacion;
}
