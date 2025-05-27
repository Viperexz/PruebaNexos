package com.viperexz.backend.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "mercancia")
@Data
public class MercanciaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMercancia;

    private String nombreMercancia;
    private int cantidadMercancia;
    private LocalDate fechaIngresoMercancia;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuarioRegistro;
}