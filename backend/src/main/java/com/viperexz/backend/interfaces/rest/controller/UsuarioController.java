package com.viperexz.backend.interfaces.rest.controller;


import com.viperexz.backend.application.dto.UsuarioRequestDTO;
import com.viperexz.backend.application.dto.UsuarioResponseDTO;
import com.viperexz.backend.application.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

    private final UsuarioUseCase usuarioUseCase;

    public UsuarioController(UsuarioUseCase usuarioUseCase) {
        this.usuarioUseCase = usuarioUseCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Long id) {
        try {
            UsuarioResponseDTO responseDTO = usuarioUseCase.consultarPorId(id);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> obtenerTodosLosUsuarios() {
        try {
            List<UsuarioResponseDTO> usuarios = usuarioUseCase.consultarTodos();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> registrarUsuario(@RequestBody UsuarioRequestDTO requestDTO) {
        try {
            UsuarioResponseDTO responseDTO = usuarioUseCase.registrarUsuario(requestDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable("id") Long id, @RequestBody UsuarioResponseDTO requestDTO) {
        try {
            UsuarioResponseDTO responseDTO = usuarioUseCase.actualizarUsuario(requestDTO, id);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable("idUsuario") Long idUsuario) {
        try {
            usuarioUseCase.eliminarUsuario(idUsuario);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}