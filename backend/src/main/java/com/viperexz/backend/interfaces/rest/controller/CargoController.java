package com.viperexz.backend.interfaces.rest.controller;

import com.viperexz.backend.application.dto.CargoDTO;
import com.viperexz.backend.application.service.CargoUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cargos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CargoController {

    private final CargoUseCase cargoUseCase;

    public CargoController(CargoUseCase cargoUseCase) {
        this.cargoUseCase = cargoUseCase;
    }

    @PostMapping
    public ResponseEntity<?> registrarCargo(@RequestBody CargoDTO cargoDTO) {
        try{
            CargoDTO responseDTO = cargoUseCase.registrarCargo(cargoDTO);
            return ResponseEntity.ok(responseDTO);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> obtenerTodosLosCargos() {
        try{
            return ResponseEntity.ok(cargoUseCase.consultarTodosLosCargos());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/{idCargo}")
    public ResponseEntity<?> eliminarCargo(@PathVariable("idCargo") Long idCargo) {
        try{
            cargoUseCase.eliminarCargo(idCargo);
            return ResponseEntity.noContent().build();
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
