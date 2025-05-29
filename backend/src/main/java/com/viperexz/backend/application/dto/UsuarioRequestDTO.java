package com.viperexz.backend.application.dto;

import com.viperexz.backend.domain.model.Cargo;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UsuarioRequestDTO {
    private String nombreUsuario;
    private int edadUsuario;
    private Long idCargoUsuario;
    private LocalDate fechaIngresoUsuario;
}
