package com.viperexz.backend.application.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UsuarioResponseDTO {
    private Long idUsuario;
    private String nombreUsuario;
    private int edadUsuario;
    private String cargoUsuario;
    private LocalDate fechaIngresoUsuario;
    private List<MercanciaResponseDTO> mercanciasUsuario;
}
