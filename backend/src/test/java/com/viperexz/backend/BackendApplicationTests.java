package com.viperexz.backend;

import com.viperexz.backend.application.dto.*;
import com.viperexz.backend.application.service.CargoUseCase;
import com.viperexz.backend.application.service.MercanciaUseCase;
import com.viperexz.backend.application.service.UsuarioUseCase;
import com.viperexz.backend.domain.model.Mercancia;
import com.viperexz.backend.exception.BusinessException;
import com.viperexz.backend.infrastructure.persistence.adapter.MercanciaRepositoryAdapter;
import com.viperexz.backend.infrastructure.persistence.entity.MercanciaEntity;
import com.viperexz.backend.infrastructure.persistence.entity.UsuarioEntity;
import com.viperexz.backend.infrastructure.persistence.repository.MercanciaJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class BackendApplicationTests {

       @Autowired
       private UsuarioUseCase usuarioUseCase;
       @Autowired
       private MercanciaUseCase mercanciaUseCase;
       @Autowired
       private CargoUseCase cargoUseCase;

       private CargoDTO cargoDTO1;
       private CargoDTO cargoDTO2;

       private UsuarioRequestDTO usuarioRequest1;
       private UsuarioRequestDTO usuarioRequest2;

       private MercanciaRequestDTO mercanciaRequest1;

       private UsuarioResponseDTO usuarioResponse1;
       private UsuarioResponseDTO usuarioResponse2;

       private MercanciaResponseDTO mercanciaResponse1;

       private CargoDTO cargoDTOResponse1;
       private CargoDTO cargoDTOResponse2;
       private MercanciaJpaRepository jpaRepository;
       private MercanciaRepositoryAdapter mercanciaRepositoryAdapter;

       @BeforeEach
       void setUp() {
           cargoDTO1 = new CargoDTO();
           cargoDTO1.setNombreCargo("Sistemas");

           cargoDTO2 = new CargoDTO();
           cargoDTO2.setNombreCargo("Gerente de Proyectos");

           usuarioRequest1 = new UsuarioRequestDTO();
           usuarioRequest1.setNombreUsuario("Julian Perez");
           usuarioRequest1.setEdadUsuario(30);
           usuarioRequest1.setFechaIngresoUsuario(LocalDate.of(2023, 10, 1));

           usuarioRequest2 = new UsuarioRequestDTO();
           usuarioRequest2.setNombreUsuario("Javier Hoyos");
           usuarioRequest2.setEdadUsuario(30);
           usuarioRequest2.setFechaIngresoUsuario(LocalDate.of(2025, 10, 1));

           mercanciaRequest1 = new MercanciaRequestDTO();
           mercanciaRequest1.setNombreMercancia("Laptop");
           mercanciaRequest1.setCantidadMercancia(10);
           mercanciaRequest1.setFechaIngresoMercancia(LocalDate.of(2023, 10, 1));
       }

       @AfterEach
       void clean()
       {
              if (usuarioResponse1 != null) {
                usuarioUseCase.eliminarUsuario(usuarioResponse1.getIdUsuario());
              }
              if (usuarioResponse2 != null) {
                usuarioUseCase.eliminarUsuario(usuarioResponse2.getIdUsuario());
              }
              if (cargoDTOResponse1 != null) {
                cargoUseCase.eliminarCargo(cargoDTOResponse1.getIdCargo());
              }
              if (cargoDTOResponse2 != null) {
                cargoUseCase.eliminarCargo(cargoDTOResponse2.getIdCargo());
              }
       }

       @Test
       void registrarUsuario() {
           cargoDTOResponse1 = cargoUseCase.registrarCargo(cargoDTO1);
           usuarioRequest1.setIdCargoUsuario(cargoDTOResponse1.getIdCargo());
           usuarioResponse1 = usuarioUseCase.registrarUsuario(usuarioRequest1);

           assert usuarioResponse1.getNombreUsuario().equals("Julian Perez");
           assert usuarioResponse1.getEdadUsuario() == 30;
           assert usuarioResponse1.getFechaIngresoUsuario().equals(LocalDate.of(2023, 10, 1));
       }

       @Test
       void consultarUsuarioId()
       {
           cargoDTOResponse1 = cargoUseCase.registrarCargo(cargoDTO1);
           usuarioRequest1.setIdCargoUsuario(cargoDTOResponse1.getIdCargo());
           usuarioResponse1 = usuarioUseCase.registrarUsuario(usuarioRequest1);
           UsuarioResponseDTO usuarioResponseConsultado = usuarioUseCase.consultarPorId(usuarioResponse1.getIdUsuario());
           assert usuarioResponseConsultado.getIdUsuario().equals(usuarioResponse1.getIdUsuario());
           assert usuarioResponseConsultado.getNombreUsuario().equals("Julian Perez");
           assert usuarioResponseConsultado.getEdadUsuario() == 30;
       }

       @Test
       void eliminacionPorOtroUsuario() {
           cargoDTOResponse1 = cargoUseCase.registrarCargo(cargoDTO1);
           cargoDTOResponse2 = cargoUseCase.registrarCargo(cargoDTO2);
           usuarioRequest1.setIdCargoUsuario(cargoDTOResponse1.getIdCargo());
           usuarioRequest2.setIdCargoUsuario(cargoDTOResponse2.getIdCargo());
           usuarioResponse1 = usuarioUseCase.registrarUsuario(usuarioRequest1);
           usuarioResponse2 = usuarioUseCase.registrarUsuario(usuarioRequest2);

               mercanciaRequest1.setIdUsuario(usuarioResponse1.getIdUsuario());
               mercanciaResponse1 = mercanciaUseCase.registrarMercancia(mercanciaRequest1);
               try {
                   System.out.println("Intentando eliminar la mercancía con el usuario 2...");
                   mercanciaUseCase.eliminarMercancia(mercanciaResponse1.getIdMercancia(), usuarioResponse2.getIdUsuario());
               } catch (Exception e) {
                   System.out.println("Excepción esperada: " + e.getMessage());
                   assert e.getMessage().contains("Solo el usuario que registró la mercancía puede eliminarla");
               }

       }

    @Test
    void eliminacionPorUsuario() {
        cargoDTOResponse1 = cargoUseCase.registrarCargo(cargoDTO1);
        usuarioRequest1.setIdCargoUsuario(cargoDTOResponse1.getIdCargo());
        usuarioResponse1 = usuarioUseCase.registrarUsuario(usuarioRequest1);

        mercanciaRequest1.setIdUsuario(usuarioResponse1.getIdUsuario());
        mercanciaResponse1 = mercanciaUseCase.registrarMercancia(mercanciaRequest1);

        System.out.println("Intentando eliminar la mercancía con el usuario 2...");
        mercanciaUseCase.eliminarMercancia(mercanciaResponse1.getIdMercancia(), usuarioResponse1.getIdUsuario());
        assertNull(mercanciaUseCase.consultarPorId(mercanciaResponse1.getIdMercancia()).getIdMercancia());
    }

    @Test
    void noDebeRegistrarMercanciaConNombreDuplicado() {
        cargoDTOResponse1 = cargoUseCase.registrarCargo(cargoDTO1);
        usuarioRequest1.setIdCargoUsuario(cargoDTOResponse1.getIdCargo());
        usuarioResponse1 = usuarioUseCase.registrarUsuario(usuarioRequest1);

        mercanciaRequest1.setIdUsuario(usuarioResponse1.getIdUsuario());
        mercanciaUseCase.registrarMercancia(mercanciaRequest1);

        Exception e = assertThrows(BusinessException.class, () -> {
            mercanciaUseCase.registrarMercancia(mercanciaRequest1);
        });

        assert(e.getMessage().contains("Ya existe una mercancía con ese nombre"));
    }

    @Test
    void noDebeRegistrarMercanciaConFechaFutura() {
        cargoDTOResponse1 = cargoUseCase.registrarCargo(cargoDTO1);
        usuarioRequest1.setIdCargoUsuario(cargoDTOResponse1.getIdCargo());
        usuarioResponse1 = usuarioUseCase.registrarUsuario(usuarioRequest1);

        mercanciaRequest1.setIdUsuario(usuarioResponse1.getIdUsuario());
        mercanciaRequest1.setFechaIngresoMercancia(LocalDate.now().plusDays(1));

        Exception e = assertThrows(BusinessException.class, () -> {
            mercanciaUseCase.registrarMercancia(mercanciaRequest1);
        });

        assert(e.getMessage().contains("La fecha de ingreso no puede ser futura"));
    }

    @Test
    void noDebeCrearUsuarioSinNombre() {
        cargoDTOResponse1 = cargoUseCase.registrarCargo(cargoDTO1);
        usuarioRequest1.setIdCargoUsuario(cargoDTOResponse1.getIdCargo());
        usuarioRequest1.setNombreUsuario("");

        Exception e = assertThrows(BusinessException.class, () -> {
            usuarioUseCase.registrarUsuario(usuarioRequest1);
        });

        assert(e.getMessage().contains("El nombre del usuario es obligatorio."));
    }

    @Test
    void debeEditarMercanciaYGuardarUsuarioYFechaDeModificacion() {
        cargoDTOResponse1 = cargoUseCase.registrarCargo(cargoDTO1);
        usuarioRequest1.setIdCargoUsuario(cargoDTOResponse1.getIdCargo());
        usuarioResponse1 = usuarioUseCase.registrarUsuario(usuarioRequest1);

        mercanciaRequest1.setIdUsuario(usuarioResponse1.getIdUsuario());
        mercanciaResponse1 = mercanciaUseCase.registrarMercancia(mercanciaRequest1);

        MercanciaRequestDTO actualizacion = new MercanciaRequestDTO();
        actualizacion.setIdUsuario(usuarioResponse1.getIdUsuario());
        actualizacion.setNombreMercancia("Laptop"); // mismo nombre, no debe cambiar
        actualizacion.setCantidadMercancia(20);
        actualizacion.setFechaIngresoMercancia(LocalDate.of(2023, 10, 1));

        MercanciaResponseDTO actualizada = mercanciaUseCase.actualizarMercancia(actualizacion,mercanciaResponse1.getIdMercancia(), usuarioResponse1.getIdUsuario());
        System.out.println("Mercancía actualizada: " + actualizada);
        assertEquals(20, actualizada.getCantidadMercancia());
        assertEquals(usuarioResponse1.getNombreUsuario(), actualizada.getNombreUsuarioModificacion());
        assertNotNull(actualizada.getFechaModificacion());
    }


    /*@Test
    void debeBuscarMercanciasPorFiltros() {
        // registrar usuario, mercancía y luego filtrar
        cargoDTOResponse1 = cargoUseCase.registrarCargo(cargoDTO1);
        usuarioRequest1.setIdCargoUsuario(cargoDTOResponse1.getIdCargo());
        usuarioResponse1 = usuarioUseCase.registrarUsuario(usuarioRequest1);

        mercanciaRequest1.setIdUsuario(usuarioResponse1.getIdUsuario());
        mercanciaUseCase.registrarMercancia(mercanciaRequest1);

        List<MercanciaResponseDTO> resultado = mercanciaUseCase.buscarMercancias(
                Optional.of("Laptop"),
                Optional.of(usuarioResponse1.getIdUsuario()),
                Optional.empty()
        );

        assertFalse(resultado.isEmpty());
    }*/

    /*@Test
    void testFindById() {
        Long id = 1L;
        MercanciaEntity entity = new MercanciaEntity();
        entity.setIdMercancia(id);
        entity.setNombreMercancia("Test Mercancia");

        when(jpaRepository.findByIdMercancia(id)).thenReturn(Optional.of(entity));

        Optional<Mercancia> result = mercanciaRepositoryAdapter.findById(id);

        assertTrue(result.isPresent());
        assertEquals("Test Mercancia", result.get().getNombreMercancia());
    }*/



}

