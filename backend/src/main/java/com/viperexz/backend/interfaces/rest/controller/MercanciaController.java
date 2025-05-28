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
    public ResponseEntity<MercanciaResponseDTO> registrarMercancia(@RequestBody MercanciaRequestDTO requestDTO) {
        MercanciaResponseDTO responseDTO = mercanciaUseCase.registrarMercancia(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<MercanciaResponseDTO>> obtenerMercancias() {
        List<MercanciaResponseDTO> mercancias = mercanciaUseCase.consultarTodos();
        return ResponseEntity.ok(mercancias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MercanciaResponseDTO> obtenerMercancia(@PathVariable Long id) {
        MercanciaResponseDTO responseDTO = mercanciaUseCase.consultarPorId(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MercanciaResponseDTO> actualizarMercancia(@PathVariable("id") Long id, @RequestBody MercanciaRequestDTO requestDTO) {
        MercanciaResponseDTO responseDTO = mercanciaUseCase.actualizarMercancia(requestDTO,id);
        return ResponseEntity.ok(responseDTO);
    }

 @DeleteMapping("/mercancia/{idMercancia}/usuario/{idUsuario}")
 public ResponseEntity<Void> eliminarMercancia(@PathVariable("idMercancia") Long idMercancia, @PathVariable("idUsuario") Long idUsuario) {
     mercanciaUseCase.eliminarMercancia(idMercancia, idUsuario);
     return ResponseEntity.noContent().build();
 }

}
