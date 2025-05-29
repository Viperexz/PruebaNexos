package com.viperexz.backend.infrastructure.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "mercancia")
@Data
public class MercanciaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mercancia")
    private Long idMercancia;

    private String nombreMercancia;
    private int cantidadMercancia;
    private LocalDate fechaIngresoMercancia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    @ToString.Exclude
    private UsuarioEntity usuarioRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_modificacion_id")
    @ToString.Exclude
    private UsuarioEntity usuarioModificacion;

    private LocalDate fechaModificacion;
}