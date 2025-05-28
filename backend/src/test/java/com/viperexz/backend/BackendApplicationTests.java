package com.viperexz.backend;

import com.viperexz.backend.application.dto.MercanciaRequestDTO;
import com.viperexz.backend.application.dto.MercanciaResponseDTO;
import com.viperexz.backend.application.dto.UsuarioRequestDTO;
import com.viperexz.backend.application.dto.UsuarioResponseDTO;
import com.viperexz.backend.application.service.MercanciaUseCase;
import com.viperexz.backend.application.service.UsuarioUseCase;
import com.viperexz.backend.infrastructure.persistence.entity.UsuarioEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private  UsuarioUseCase usuarioUseCase;
    @Autowired
    private  MercanciaUseCase mercanciaUseCase;


    private final UsuarioRequestDTO usuarioRequest1;
    private final UsuarioRequestDTO usuarioRequest2;

    private final MercanciaRequestDTO mercanciaRequest1;



    private UsuarioResponseDTO usuarioResponse1;
    private UsuarioResponseDTO usuarioResponse2;

    private MercanciaResponseDTO mercanciaResponse1;


    public BackendApplicationTests() {

        // Inicializar el objeto usuarioRequest1 con valores de prueba
        usuarioRequest1 = new UsuarioRequestDTO();
        usuarioRequest1.setNombreUsuario("Julian Perez");
        usuarioRequest1.setEdadUsuario(30);
        usuarioRequest1.setCargoUsuario("Desarrollador");
        usuarioRequest1.setFechaIngresoUsuario(LocalDate.of(2023, 10, 1));
        usuarioResponse1 = new UsuarioResponseDTO();

        // Inicializar el objeto mercanciaRequest1 con valores de prueba
        mercanciaRequest1 = new MercanciaRequestDTO();
        mercanciaRequest1.setNombreMercancia("Laptop");
        mercanciaRequest1.setCantidadMercancia(10);
        mercanciaRequest1.setFechaIngresoMercancia(LocalDate.of(2023, 10, 1));






        //Inicializar el objeto usuarioRequest2 con valores de prueba
        usuarioRequest2 = new UsuarioRequestDTO();
        usuarioRequest2.setNombreUsuario("Javier Hoyos");
        usuarioRequest2.setEdadUsuario(30);
        usuarioRequest2.setCargoUsuario("Desarrollador");
        usuarioRequest2.setFechaIngresoUsuario(LocalDate.of(2025, 10, 1));
        usuarioResponse2 = new UsuarioResponseDTO();

    }

    @Test
    void eliminacionPorOtroUsuario() {

        usuarioResponse1 = usuarioUseCase.registrarUsuario(usuarioRequest1);
        usuarioResponse2 = usuarioUseCase.registrarUsuario(usuarioRequest2);
       try{
           //Se registran los productos.
           mercanciaRequest1.setIdUsuario(usuarioResponse1.getIdUsuario());
           mercanciaResponse1 = mercanciaUseCase.registrarMercancia(mercanciaRequest1);

           //Se intenta eliminar el producto con el usuario 2, que no es el dueño del producto.
           try {
               System.out.println("Intentando eliminar la mercancía con el usuario 2...");
               mercanciaUseCase.eliminarMercancia(mercanciaResponse1.getIdMercancia(), usuarioResponse2.getIdUsuario());
           } catch (Exception e) {
                System.out.println("Excepción esperada: " + e.getMessage());
               assert e.getMessage().contains("Solo el usuario que registró la mercancía puede eliminarla");
               mercanciaUseCase.eliminarMercancia(mercanciaResponse1.getIdMercancia(), usuarioResponse1.getIdUsuario());
               usuarioUseCase.eliminarUsuario(usuarioResponse1.getIdUsuario());
               usuarioUseCase.eliminarUsuario(usuarioResponse2.getIdUsuario());
           }
       }catch (Exception e) {
           usuarioUseCase.eliminarUsuario(usuarioResponse1.getIdUsuario());
           usuarioUseCase.eliminarUsuario(usuarioResponse2.getIdUsuario());
           throw e; // Re-lanzar la excepción para que falle el test
       }
    }
}
