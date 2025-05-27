package com.viperexz.backend.application.dto;

import com.viperexz.backend.application.dto.MercanciaResponseDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UsuarioDTO {
    private Long idUsuario;
    private String nombreUsuario;
    private int edadUsuario;
    private String cargoUsuario;
    private LocalDate fechaIngresoUsuario;
    private List<MercanciaResponseDTO> mercanciasUsuario;
}
