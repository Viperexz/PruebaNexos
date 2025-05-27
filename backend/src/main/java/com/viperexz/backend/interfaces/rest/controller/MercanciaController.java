package com.viperexz.backend.interfaces.rest.controller;

import com.viperexz.backend.application.dto.MercanciaRequestDTO;
import com.viperexz.backend.application.dto.MercanciaResponseDTO;
import com.viperexz.backend.application.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mercancias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MercanciaController {

    private final CrearMercanciaUseCase crearMercanciaUseCase;
    private final ConsultarMercanciaUseCase consultarMercanciaUseCase;
    private final ConsultarTodasLasMercanciasUseCase consultarTodasLasMercanciasUseCase;


    public MercanciaController(CrearMercanciaUseCase crearMercanciaUseCase,
                               ConsultarMercanciaUseCase consultarMercanciaUseCase,
                               ConsultarTodasLasMercanciasUseCase consultarTodasLasMercanciasUseCase) {
        this.crearMercanciaUseCase = crearMercanciaUseCase;
        this.consultarMercanciaUseCase = consultarMercanciaUseCase;
        this.consultarTodasLasMercanciasUseCase = consultarTodasLasMercanciasUseCase;
    }

    @PostMapping
    public ResponseEntity<MercanciaResponseDTO> registrarMercancia(@RequestBody MercanciaRequestDTO requestDTO) {
        MercanciaResponseDTO responseDTO = crearMercanciaUseCase.registrar(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<MercanciaResponseDTO>> obtenerMercancias() {
        List<MercanciaResponseDTO> mercancias = consultarTodasLasMercanciasUseCase.consultarTodos();
        return ResponseEntity.ok(mercancias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MercanciaResponseDTO> obtenerMercancia(@PathVariable Long id) {
        MercanciaResponseDTO responseDTO = consultarMercanciaUseCase.consultarPorId(id);
        return ResponseEntity.ok(responseDTO);
    }

}
