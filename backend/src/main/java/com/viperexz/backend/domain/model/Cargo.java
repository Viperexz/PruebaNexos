package com.viperexz.backend.domain.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cargo {
    private Long idCargo;
    private String nombreCargo;
}
