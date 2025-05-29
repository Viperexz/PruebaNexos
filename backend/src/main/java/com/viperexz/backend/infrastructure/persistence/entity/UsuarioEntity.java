package com.viperexz.backend.infrastructure.persistence.entity;

import com.viperexz.backend.domain.model.Mercancia;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;
    private String nombreUsuario;
    private int edadUsuario;
    @ManyToOne
    @JoinColumn(name = "cargo_usuario_id_cargo")
    private CargoEntity cargoUsuario;
    private LocalDate fechaIngresoUsuario;

    @OneToMany(mappedBy = "usuarioRegistro", cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<MercanciaEntity> mercancias;

}
