package com.viperexz.backend.interfaces.rest.controller;

import com.viperexz.backend.application.dto.UsuarioDTO;
import com.viperexz.backend.application.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

    private final ConsultarUsuarioUseCase consultarUsuarioUseCase;
    private final ConsultarTodosLosUsuariosUseCase consultarTodosLosUsuariosUseCase;

    public UsuarioController(ConsultarUsuarioUseCase consultarUsuarioUseCase, ConsultarTodosLosUsuariosUseCase consultarTodosLosUsuariosUseCase) {
        this.consultarUsuarioUseCase = consultarUsuarioUseCase;
        this.consultarTodosLosUsuariosUseCase = consultarTodosLosUsuariosUseCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable Long id) {
        UsuarioDTO responseDTO = consultarUsuarioUseCase.consultarPorId(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerTodosLosUsuarios() {
        List<UsuarioDTO> usuarios = consultarTodosLosUsuariosUseCase.consultarTodos();
        return ResponseEntity.ok(usuarios);
    }
}