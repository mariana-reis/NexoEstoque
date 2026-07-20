package com.gestaoestoque.api.controller;

import com.gestaoestoque.api.domain.Produto;
import com.gestaoestoque.api.domain.TipoProduto;
import com.gestaoestoque.api.dto.ConsultaTipoDTO;
import com.gestaoestoque.api.service.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@Tag(name = "Produto", description = "API para gestão de controle de estoque")
public class ProdutoController {
  
  private final ProdutoService service;
  
  @PostMapping
  @Operation(summary = "Criar um novo produto")
  public ResponseEntity<Produto> criar(@RequestBody @Valid Produto produto) {
    return new ResponseEntity<>(service.salvar(produto), HttpStatus.CREATED);
  }
  
  @GetMapping
  @Operation(summary = "Listar todos os produtos (paginado). Suporta filtro por tipo via ?tipo=ELETRONICO")
  public ResponseEntity<Page<Produto>> listar(@RequestParam(required = false) TipoProduto tipo, Pageable pageable) {
    
    if (tipo != null) {
      return ResponseEntity.ok(service.buscarPorTipo(tipo, pageable));
    }
    
    return ResponseEntity.ok(service.listarProdutos(pageable));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Buscar produto por ID")
  public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
    return ResponseEntity.ok(service.buscarPorId(id));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Atualizar um produto existente")
  public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody @Valid Produto produto) {
    return ResponseEntity.ok(service.atualizar(id, produto));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Remover um produto")
  public ResponseEntity<Void> deletar(@PathVariable Long id) {
    service.deletar(id);
    return ResponseEntity.noContent().build();
  }
  
  @GetMapping("/por-tipo")
  @Operation(summary = "Consulta de produtos agrupados por tipo, com estoque disponível e total de saídas")
  public ResponseEntity<List<ConsultaTipoDTO>> consultarPorTipo() {
    return ResponseEntity.ok(service.consultarPorTipo());
  }
}
