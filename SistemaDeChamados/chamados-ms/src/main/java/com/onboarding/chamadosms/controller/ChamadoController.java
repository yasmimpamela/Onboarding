package com.onboarding.chamadosms.controller;

import com.onboarding.chamadosms.dto.ChamadoResponseDTO;
import com.onboarding.chamadosms.model.Chamado;
import com.onboarding.chamadosms.repository.ChamadoRepository;
import com.onboarding.chamadosms.servise.ChamadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chamados")
public class ChamadoController {

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private ChamadoService chamadoService;

    /**
     * Endpoint para criar um novo chamado.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Chamado criar(@RequestBody Chamado chamado) {
        return chamadoRepository.save(chamado);
    }

    /**
     * Endpoint para listar todos os chamados.
     */
    @GetMapping
    public List<ChamadoResponseDTO> listar() {
        return chamadoService.listarChamadosCompletos();
    }

    /**
     * Endpoint para buscar um chamado específico pelo ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ChamadoResponseDTO> buscarPorId(@PathVariable Long id) {
        try {
            ChamadoResponseDTO response = chamadoService.buscarChamadoCompleto(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}