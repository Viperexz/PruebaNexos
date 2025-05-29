package com.viperexz.backend;

import com.viperexz.backend.application.dto.*;
import com.viperexz.backend.application.service.CargoUseCase;
import com.viperexz.backend.application.service.MercanciaUseCase;
import com.viperexz.backend.application.service.UsuarioUseCase;
import com.viperexz.backend.domain.model.Mercancia;
import com.viperexz.backend.exception.BusinessException;
import com.viperexz.backend.exception.NotFoundException;
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
import java.util.UUID;

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
        String uniqueSuffix = UUID.randomUUID().toString().substring(0, 5);
        cargoDTO1 = new CargoDTO();
        cargoDTO1.setNombreCargo("Sistemas_" + uniqueSuffix);

        cargoDTO2 = new CargoDTO();
        cargoDTO2.setNombreCargo("Gerente_" + uniqueSuffix);

        usuarioRequest1 = new UsuarioRequestDTO();
        usuarioRequest1.setNombreUsuario("Julian_" + uniqueSuffix);
        usuarioRequest1.setEdadUsuario(30);
        usuarioRequest1.setFechaIngresoUsuario(LocalDate.of(2023, 10, 1));

        usuarioRequest2 = new UsuarioRequestDTO();
        usuarioRequest2.setNombreUsuario("Javier_" + uniqueSuffix);
        usuarioRequest2.setEdadUsuario(30);
        usuarioRequest2.setFechaIngresoUsuario(LocalDate.of(2024, 10, 1));

        mercanciaRequest1 = new MercanciaRequestDTO();
        mercanciaRequest1.setNombreMercancia("Laptop_" + uniqueSuffix);
        mercanciaRequest1.setCantidadMercancia(10);
        mercanciaRequest1.setFechaIngresoMercancia(LocalDate.of(2023, 10, 1));
    }


    @AfterEach
     void clean() {
             try {
                 if (mercanciaResponse1 != null && usuarioResponse1 != null) {
                     mercanciaUseCase.eliminarMercancia(mercanciaResponse1.getIdMercancia(), usuarioResponse1.getIdUsuario());
                 }
             } catch (Exception e) {
                 System.out.println("Error al eliminar mercancía (puede haber sido eliminada previamente): " + e.getMessage());
             }

             try {
                 if (usuarioResponse1 != null) {
                     usuarioUseCase.eliminarUsuario(usuarioResponse1.getIdUsuario());
                 }
             } catch (Exception e) {
                 System.out.println("Error al eliminar usuarioResponse1: " + e.getMessage());
             }

             try {
                 if (usuarioResponse2 != null) {
                     usuarioUseCase.eliminarUsuario(usuarioResponse2.getIdUsuario());
                 }
             } catch (Exception e) {
                 System.out.println("Error al eliminar usuarioResponse2: " + e.getMessage());
             }

             try {
                 if (cargoDTOResponse1 != null) {
                     cargoUseCase.eliminarCargo(cargoDTOResponse1.getIdCargo());
                 }
             } catch (Exception e) {
                 System.out.println("Error al eliminar cargoDTOResponse1: " + e.getMessage());
             }

             try {
                 if (cargoDTOResponse2 != null) {
                     cargoUseCase.eliminarCargo(cargoDTOResponse2.getIdCargo());
                 }
             } catch (Exception e) {
                 System.out.println("Error al eliminar cargoDTOResponse2: " + e.getMessage());
             }
     }

       @Test
       void consultarTodosLosUsuarios() {
           cargoDTOResponse1 = cargoUseCase.registrarCargo(cargoDTO1);
           usuarioRequest1.setIdCargoUsuario(cargoDTOResponse1.getIdCargo());
           usuarioResponse1 = usuarioUseCase.registrarUsuario(usuarioRequest1);

           List<UsuarioResponseDTO> usuarios = usuarioUseCase.consultarTodos();
           assertFalse(usuarios.isEmpty());
           assertTrue(usuarios.stream().anyMatch(u -> u.getIdUsuario().equals(usuarioResponse1.getIdUsuario())));
       }

       @Test
       void actualizarUsuario() {
           cargoDTOResponse1 = cargoUseCase.registrarCargo(cargoDTO1);
           usuarioRequest1.setIdCargoUsuario(cargoDTOResponse1.getIdCargo());
           usuarioResponse1 = usuarioUseCase.registrarUsuario(usuarioRequest1);

           UsuarioRequestDTO actualizacion = new UsuarioRequestDTO();
           actualizacion.setNombreUsuario("Julian Actualizado");
           actualizacion.setEdadUsuario(35);
           actualizacion.setFechaIngresoUsuario(LocalDate.of(2023, 10, 2));
           actualizacion.setIdCargoUsuario(cargoDTOResponse1.getIdCargo());

           UsuarioResponseDTO usuarioActualizado = usuarioUseCase.actualizarUsuario(actualizacion, usuarioResponse1.getIdUsuario());
           assertEquals("Julian Actualizado", usuarioActualizado.getNombreUsuario());
           assertEquals(35, usuarioActualizado.getEdadUsuario());
           assertEquals(LocalDate.of(2023, 10, 2), usuarioActualizado.getFechaIngresoUsuario());
       }

       @Test
       void eliminarUsuarioInexistente() {

           try {
               usuarioUseCase.eliminarUsuario(999L);
           } catch (Exception e) {
               System.out.println("Excepción esperada: " + e.getMessage());
               assert e.getMessage().contains("Usuario no encontrado");
           }
       }

       @Test
       void consultarUsuarioInexistente() {
           try {
               usuarioUseCase.consultarPorId(999L); // ID inexistente
           } catch (Exception e) {
               System.out.println("Excepción esperada: " + e.getMessage());
               assert e.getMessage().contains("Usuario no encontrado");
           }
       }

    @Test
    void registrarUsuario() {
        String unique = UUID.randomUUID().toString().substring(0, 5);
        cargoDTO1.setNombreCargo("Sistemas_" + unique);
        cargoDTOResponse1 = cargoUseCase.registrarCargo(cargoDTO1);

        usuarioRequest1.setNombreUsuario("Julian_" + unique);
        usuarioRequest1.setIdCargoUsuario(cargoDTOResponse1.getIdCargo());
        usuarioResponse1 = usuarioUseCase.registrarUsuario(usuarioRequest1);

        assert usuarioResponse1.getNombreUsuario().equals("Julian_" + unique);
        assert usuarioResponse1.getEdadUsuario() == 30;
        assert usuarioResponse1.getFechaIngresoUsuario().equals(LocalDate.of(2023, 10, 1));
    }


    @Test
    void consultarUsuarioId() {
        String unique = UUID.randomUUID().toString().substring(0, 5);
        cargoDTO1.setNombreCargo("Sistemas_" + unique);
        cargoDTOResponse1 = cargoUseCase.registrarCargo(cargoDTO1);

        usuarioRequest1.setNombreUsuario("Julian_" + unique);
        usuarioRequest1.setIdCargoUsuario(cargoDTOResponse1.getIdCargo());
        usuarioResponse1 = usuarioUseCase.registrarUsuario(usuarioRequest1);

        UsuarioResponseDTO usuarioResponseConsultado = usuarioUseCase.consultarPorId(usuarioResponse1.getIdUsuario());
        assertEquals(usuarioResponse1.getIdUsuario(), usuarioResponseConsultado.getIdUsuario());
        assertEquals(usuarioResponse1.getNombreUsuario(), usuarioResponseConsultado.getNombreUsuario());
        assertEquals(usuarioResponse1.getEdadUsuario(), usuarioResponseConsultado.getEdadUsuario());
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
    void registrarMercancia() {
        String unique = UUID.randomUUID().toString().substring(0, 5); // o System.currentTimeMillis()

        cargoDTO1.setNombreCargo("Sistemas_" + unique);
        cargoDTOResponse1 = cargoUseCase.registrarCargo(cargoDTO1);

        usuarioRequest1.setNombreUsuario("Julian_" + unique);
        usuarioRequest1.setIdCargoUsuario(cargoDTOResponse1.getIdCargo());
        usuarioResponse1 = usuarioUseCase.registrarUsuario(usuarioRequest1);

        mercanciaRequest1.setNombreMercancia("Laptop_" + unique);
        mercanciaRequest1.setIdUsuario(usuarioResponse1.getIdUsuario());
        mercanciaResponse1 = mercanciaUseCase.registrarMercancia(mercanciaRequest1);

        assertEquals("Laptop_" + unique, mercanciaResponse1.getNombreMercancia());
        assertEquals(10, mercanciaResponse1.getCantidadMercancia());
        assertEquals(usuarioResponse1.getNombreUsuario(), mercanciaResponse1.getNombreUsuarioRegistro());
    }


    @Test
       void actualizarMercancia() {
           cargoDTOResponse1 = cargoUseCase.registrarCargo(cargoDTO1);
           usuarioRequest1.setIdCargoUsuario(cargoDTOResponse1.getIdCargo());
           usuarioResponse1 = usuarioUseCase.registrarUsuario(usuarioRequest1);

           mercanciaRequest1.setIdUsuario(usuarioResponse1.getIdUsuario());
           mercanciaResponse1 = mercanciaUseCase.registrarMercancia(mercanciaRequest1);

           MercanciaRequestDTO actualizacion = new MercanciaRequestDTO();
           actualizacion.setIdUsuario(usuarioResponse1.getIdUsuario());
           actualizacion.setNombreMercancia("Laptop Actualizada");
           actualizacion.setCantidadMercancia(15);
           actualizacion.setFechaIngresoMercancia(LocalDate.of(2023, 10, 1));

           MercanciaResponseDTO actualizada = mercanciaUseCase.actualizarMercancia(actualizacion, mercanciaResponse1.getIdMercancia(), usuarioResponse1.getIdUsuario());
           assertEquals("Laptop Actualizada", actualizada.getNombreMercancia());
           assertEquals(15, actualizada.getCantidadMercancia());
       }

    @Test
    void eliminarMercancia() {
        cargoDTOResponse1 = cargoUseCase.registrarCargo(cargoDTO1);
        usuarioRequest1.setIdCargoUsuario(cargoDTOResponse1.getIdCargo());
        usuarioResponse1 = usuarioUseCase.registrarUsuario(usuarioRequest1);

        mercanciaRequest1.setIdUsuario(usuarioResponse1.getIdUsuario());
        mercanciaResponse1 = mercanciaUseCase.registrarMercancia(mercanciaRequest1);

        // Eliminar la mercancía
        mercanciaUseCase.eliminarMercancia(mercanciaResponse1.getIdMercancia(), usuarioResponse1.getIdUsuario());

        assertThrows(NotFoundException.class, () -> {
            mercanciaUseCase.consultarPorId(mercanciaResponse1.getIdMercancia());
        });
    }

    @Test
    void consultarMercanciaPorId() {
        String unique = UUID.randomUUID().toString().substring(0, 5);

        cargoDTO1.setNombreCargo("Sistemas_" + unique);
        cargoDTOResponse1 = cargoUseCase.registrarCargo(cargoDTO1);

        usuarioRequest1.setNombreUsuario("Julian_" + unique);
        usuarioRequest1.setIdCargoUsuario(cargoDTOResponse1.getIdCargo());
        usuarioResponse1 = usuarioUseCase.registrarUsuario(usuarioRequest1);

        mercanciaRequest1.setNombreMercancia("Laptop_" + unique);
        mercanciaRequest1.setIdUsuario(usuarioResponse1.getIdUsuario());
        mercanciaResponse1 = mercanciaUseCase.registrarMercancia(mercanciaRequest1);

        MercanciaResponseDTO consultada = mercanciaUseCase.consultarPorId(mercanciaResponse1.getIdMercancia());
        assertEquals(mercanciaResponse1.getIdMercancia(), consultada.getIdMercancia());
        assertEquals("Laptop_" + unique, consultada.getNombreMercancia());
    }


    @Test
    void eliminacionPorUsuario() {
        cargoDTOResponse1 = cargoUseCase.registrarCargo(cargoDTO1);
        usuarioRequest1.setIdCargoUsuario(cargoDTOResponse1.getIdCargo());
        usuarioResponse1 = usuarioUseCase.registrarUsuario(usuarioRequest1);

        mercanciaRequest1.setIdUsuario(usuarioResponse1.getIdUsuario());
        mercanciaResponse1 = mercanciaUseCase.registrarMercancia(mercanciaRequest1);

        mercanciaUseCase.eliminarMercancia(
                mercanciaResponse1.getIdMercancia(),
                usuarioResponse1.getIdUsuario()
        );
        assertThrows(NotFoundException.class, () -> {
            mercanciaUseCase.consultarPorId(mercanciaResponse1.getIdMercancia());
        });
    }

    @Test
    void noDebeRegistrarMercanciaConNombreDuplicado() {
        cargoDTOResponse1 = cargoUseCase.registrarCargo(cargoDTO1);
        usuarioRequest1.setIdCargoUsuario(cargoDTOResponse1.getIdCargo());
        usuarioResponse1 = usuarioUseCase.registrarUsuario(usuarioRequest1);

        mercanciaRequest1.setIdUsuario(usuarioResponse1.getIdUsuario());
        mercanciaUseCase.registrarMercancia(mercanciaRequest1);

        try{
            mercanciaUseCase.registrarMercancia(mercanciaRequest1);
        }catch (Exception e){
            assert(e.getMessage().contains("Ya existe una mercancía con ese nombre"));
        }
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
        String uniqueSuffix = UUID.randomUUID().toString().substring(0, 5);

        // Crear un cargo válido
        cargoDTO1.setNombreCargo("Soporte-" + uniqueSuffix);
        cargoDTOResponse1 = cargoUseCase.registrarCargo(cargoDTO1);

        // Usuario sin nombre
        usuarioRequest1.setNombreUsuario("");
        usuarioRequest1.setIdCargoUsuario(cargoDTOResponse1.getIdCargo());

        BusinessException e = assertThrows(BusinessException.class, () -> {
            usuarioUseCase.registrarUsuario(usuarioRequest1);
        });
        assertTrue(e.getMessage().contains("El nombre es obligatorio."));
    }

    @Test
    void debeEditarMercanciaYGuardarUsuarioYFechaDeModificacion() {
        String uniqueSuffix = UUID.randomUUID().toString().substring(0, 5);

        cargoDTO1.setNombreCargo("Sistemas-" + uniqueSuffix);
        cargoDTOResponse1 = cargoUseCase.registrarCargo(cargoDTO1);

        usuarioRequest1.setNombreUsuario("Julian-" + uniqueSuffix);
        usuarioRequest1.setIdCargoUsuario(cargoDTOResponse1.getIdCargo());
        usuarioResponse1 = usuarioUseCase.registrarUsuario(usuarioRequest1);


        mercanciaRequest1.setNombreMercancia("Laptop-" + uniqueSuffix);
        mercanciaRequest1.setIdUsuario(usuarioResponse1.getIdUsuario());
        mercanciaResponse1 = mercanciaUseCase.registrarMercancia(mercanciaRequest1);

        MercanciaRequestDTO actualizacion = new MercanciaRequestDTO();
        actualizacion.setIdUsuario(usuarioResponse1.getIdUsuario());
        actualizacion.setNombreMercancia(mercanciaRequest1.getNombreMercancia()); // mismo nombre
        actualizacion.setCantidadMercancia(20);
        actualizacion.setFechaIngresoMercancia(LocalDate.of(2023, 10, 1));

        // Ejecutar actualización
        MercanciaResponseDTO actualizada = mercanciaUseCase.actualizarMercancia(
                actualizacion,
                mercanciaResponse1.getIdMercancia(),
                usuarioResponse1.getIdUsuario()
        );

        // Verificar resultados
        assertEquals(20, actualizada.getCantidadMercancia());
        assertEquals(usuarioResponse1.getNombreUsuario(), actualizada.getNombreUsuarioModificacion());
        assertNotNull(actualizada.getFechaModificacion());
        assertEquals(mercanciaRequest1.getNombreMercancia(), actualizada.getNombreMercancia());
    }


    @Test
    void noDebeEliminarCargoConUsuariosAsociados() {
        cargoDTOResponse1 = cargoUseCase.registrarCargo(cargoDTO1);
        usuarioRequest1.setIdCargoUsuario(cargoDTOResponse1.getIdCargo());
        usuarioResponse1 = usuarioUseCase.registrarUsuario(usuarioRequest1);

        Exception e = assertThrows(BusinessException.class, () -> {
            cargoUseCase.eliminarCargo(cargoDTOResponse1.getIdCargo());
        });

        assert(e.getMessage().contains("No se puede eliminar el cargo porque tiene usuarios asociados."));
    }








}

