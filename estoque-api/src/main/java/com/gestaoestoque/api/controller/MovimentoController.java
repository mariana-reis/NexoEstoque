package com.gestaoestoque.api.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gestaoestoque.api.domain.MovimentoEstoque;
import com.gestaoestoque.api.dto.LucroProdutoDTO;
import com.gestaoestoque.api.service.MovimentoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/movimentos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Movimentação", description = "Controle de entrada e saída de estoque e consulta de lucro")
public class MovimentoController {

  private final MovimentoService service;
  
  @PostMapping
  @Operation(summary = "Registrar entrada ou saída de estoque")
  public ResponseEntity<MovimentoEstoque> registrar(@RequestBody @Valid MovimentoEstoque movimento) {
    return new ResponseEntity<>(service.registrar(movimento), HttpStatus.CREATED);
  }
  
  @GetMapping
  @Operation(summary = "Listar histórico de movimentações (paginado)")
  public ResponseEntity<Page<MovimentoEstoque>> listar(Pageable pageable) {
    return ResponseEntity.ok(service.listar(pageable));
  }
  
  @GetMapping("/{id}")
  @Operation(summary = "Buscar movimentação por ID")
  public ResponseEntity<MovimentoEstoque> buscarPorId(@PathVariable Long id) {
    return ResponseEntity.ok(service.buscarPorId(id));
  }
  
  @GetMapping("/lucro")
  @Operation(summary = "Consulta de lucro por produto: quantidade vendida e lucro total acumulado")
  public ResponseEntity<List<LucroProdutoDTO>> consultarLucro() {
    return ResponseEntity.ok(service.consultarLucro());
  }
}
