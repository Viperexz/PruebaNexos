package com.viperexz.backend.domain.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Usuario {
    private Long idUsuario;
    private String nombreUsuario;
    private int edadUsuario;
    private String cargoUsuario;
    private LocalDate fechaIngresoUsuario;
    private List<Mercancia> mercanciasUsuario;
}
