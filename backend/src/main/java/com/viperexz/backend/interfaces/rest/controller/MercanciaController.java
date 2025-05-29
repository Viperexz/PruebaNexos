package com.viperexz.backend.interfaces.rest.controller;

import com.viperexz.backend.application.dto.MercanciaRequestDTO;
import com.viperexz.backend.application.dto.MercanciaResponseDTO;
import com.viperexz.backend.application.service.*;
import com.viperexz.backend.domain.model.Mercancia;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mercancias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MercanciaController {

    private final MercanciaUseCase mercanciaUseCase;
    


    public MercanciaController(MercanciaUseCase crearMercanciaUseCase) {
        this.mercanciaUseCase = crearMercanciaUseCase;
    }

  @PostMapping
  public ResponseEntity<?> registrarMercancia(@RequestBody MercanciaRequestDTO requestDTO) {
      try {
          MercanciaResponseDTO responseDTO = mercanciaUseCase.registrarMercancia(requestDTO);
          return ResponseEntity.ok(responseDTO);
      } catch (Exception e) {
          return ResponseEntity.badRequest().body(e.getMessage());
      }
  }

  @GetMapping
  public ResponseEntity<?> obtenerMercancias() {
      try {
          List<MercanciaResponseDTO> mercancias = mercanciaUseCase.consultarTodos();
          return ResponseEntity.ok(mercancias);
      } catch (Exception e) {
          return ResponseEntity.badRequest().body(e.getMessage());
      }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> obtenerMercancia(@PathVariable Long id) {
      try {
          MercanciaResponseDTO responseDTO = mercanciaUseCase.consultarPorId(id);
          return ResponseEntity.ok(responseDTO);
      } catch (Exception e) {
          return ResponseEntity.badRequest().body(e.getMessage());
      }
  }

  @PutMapping("/{idMercancia}/usuario/{idUsuario}")
  public ResponseEntity<?> actualizarMercancia(@PathVariable("idMercancia") Long idMercancia, @PathVariable("idUsuario") Long idUsuario, @RequestBody MercanciaRequestDTO requestDTO) {
      try {
          MercanciaResponseDTO responseDTO = mercanciaUseCase.actualizarMercancia(requestDTO, idMercancia, idUsuario);
          return ResponseEntity.ok(responseDTO);
      } catch (Exception e) {
          return ResponseEntity.badRequest().body(e.getMessage());
      }
  }

  @DeleteMapping("/{idMercancia}/usuario/{idUsuario}")
  public ResponseEntity<?> eliminarMercancia(@PathVariable("idMercancia") Long idMercancia, @PathVariable("idUsuario") Long idUsuario) {
      try {
          mercanciaUseCase.eliminarMercancia(idMercancia, idUsuario);
          return ResponseEntity.noContent().build();
      } catch (Exception e) {
          return ResponseEntity.badRequest().body(e.getMessage());
      }
  }

}
