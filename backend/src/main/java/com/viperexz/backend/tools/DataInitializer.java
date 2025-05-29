package com.viperexz.backend.tools;

import com.viperexz.backend.application.dto.CargoDTO;
import com.viperexz.backend.application.dto.MercanciaRequestDTO;
import com.viperexz.backend.application.dto.UsuarioRequestDTO;
import com.viperexz.backend.application.service.CargoUseCase;
import com.viperexz.backend.application.service.MercanciaUseCase;
import com.viperexz.backend.application.service.UsuarioUseCase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CargoUseCase cargoUseCase;
    private final UsuarioUseCase usuarioUseCase;
    private final MercanciaUseCase mercanciaUseCase;

    public DataInitializer(CargoUseCase cargoUseCase, UsuarioUseCase usuarioUseCase, MercanciaUseCase mercanciaUseCase) {
        this.cargoUseCase = cargoUseCase;
        this.usuarioUseCase = usuarioUseCase;
        this.mercanciaUseCase = mercanciaUseCase;
    }

    @Override
    public void run(String... args) throws Exception {
        if (cargoUseCase.consultarTodosLosCargos().isEmpty()) {
            CargoDTO cargo1 = new CargoDTO();
            cargo1.setNombreCargo("Administrador");
            cargoUseCase.registrarCargo(cargo1);

            CargoDTO cargo2 = new CargoDTO();
            cargo2.setNombreCargo("Operador");
            cargoUseCase.registrarCargo(cargo2);
        }

        if (usuarioUseCase.consultarTodos().isEmpty()) {
            UsuarioRequestDTO usuario1 = new UsuarioRequestDTO();
            usuario1.setNombreUsuario("admin");
            usuario1.setEdadUsuario(30);
            usuario1.setFechaIngresoUsuario(LocalDate.now());
            usuario1.setIdCargoUsuario(cargoUseCase.consultarPorNombre("Administrador").getIdCargo());
            usuarioUseCase.registrarUsuario(usuario1);

            UsuarioRequestDTO usuario2 = new UsuarioRequestDTO();
            usuario2.setNombreUsuario("operador");
            usuario2.setEdadUsuario(25);
            usuario2.setFechaIngresoUsuario(LocalDate.now());
            usuario2.setIdCargoUsuario(cargoUseCase.consultarPorNombre("Operador").getIdCargo());
            usuarioUseCase.registrarUsuario(usuario2);
        }


        if (mercanciaUseCase.consultarTodos().isEmpty()) {
            MercanciaRequestDTO mercancia1 = new MercanciaRequestDTO();
            mercancia1.setNombreMercancia("Laptop");
            mercancia1.setCantidadMercancia(10);
            mercancia1.setIdUsuario(usuarioUseCase.consultarPorNombre("admin").getIdUsuario());
            mercancia1.setFechaIngresoMercancia(LocalDate.now());
            mercanciaUseCase.registrarMercancia(mercancia1);

            MercanciaRequestDTO mercancia2 = new MercanciaRequestDTO();
            mercancia2.setNombreMercancia("Monitor");
            mercancia2.setCantidadMercancia(5);
            mercancia2.setFechaIngresoMercancia(LocalDate.now());
            mercancia2.setIdUsuario(usuarioUseCase.consultarPorNombre("operador").getIdUsuario());
            mercanciaUseCase.registrarMercancia(mercancia2);
        }
    }
}