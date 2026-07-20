package com.gestaoestoque.api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gestaoestoque.api.domain.Produto;
import com.gestaoestoque.api.exception.ResourceNotFoundException;
import com.gestaoestoque.api.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {
  @Mock
  private ProdutoRepository repository;

  @InjectMocks
  private ProdutoService service;

  @Test
  @DisplayName("Deve retornar produto quando ID existir")
  void buscarPorId_Sucesso() {
    Long id = 1L;
    Produto produtoMock = new Produto();
    produtoMock.setId(id);
    when(repository.findById(id)).thenReturn(Optional.of(produtoMock));

    Produto resultado = service.buscarPorId(id);
    
    assertNotNull(resultado);
    assertEquals(id, resultado.getId());
    verify(repository, times(1)).findById(id);
  }

  @Test
  @DisplayName("Deve lançar exceção quando ID não existir")
  void buscarPorId_Falha() {
    Long id = 1L;
    when(repository.findById(id)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> service.buscarPorId(id));
    verify(repository, times(1)).findById(id);
  }
  
  @Test
  @DisplayName("Deve deletar produto com sucesso quando ID existir")
  void deletar_Sucesso() {
    Long id = 1L;
    when(repository.existsById(id)).thenReturn(true);
    doNothing().when(repository).deleteById(id);

    service.deletar(id);

    verify(repository, times(1)).existsById(id);
    verify(repository, times(1)).deleteById(id);
  }

  @Test
  @DisplayName("Deve lançar exceção ao tentar deletar ID inexistente")
  void deletar_Falha() {
    Long id = 99L;
    when(repository.existsById(id)).thenReturn(false);
  
    assertThrows(ResourceNotFoundException.class, () -> service.deletar(id));
    verify(repository, times(1)).existsById(id);
    verify(repository, never()).deleteById(id);
  }
  
  @Test
  @DisplayName("Deve salvar produto com sucesso")
  void salvar_Sucesso() {
    Produto produto = new Produto();
    produto.setNome("Teclado Mecânico");
    when(repository.save(any(Produto.class))).thenReturn(produto);

    Produto resultado = service.salvar(produto);

    assertNotNull(resultado);
    assertEquals("Teclado Mecânico", resultado.getNome());
    verify(repository, times(1)).save(produto);
  }

  @Test
  @DisplayName("Deve atualizar produto com sucesso")
  void atualizar_Sucesso() {
    Long id = 1L;
    Produto produtoExistente = new Produto();
    produtoExistente.setId(id);
    produtoExistente.setNome("Mouse Antigo");

    Produto produtoAtualizado = new Produto();
    produtoAtualizado.setNome("Mouse Gamer");

    when(repository.findById(id)).thenReturn(Optional.of(produtoExistente));
    when(repository.save(any(Produto.class))).thenReturn(produtoExistente);

    Produto resultado = service.atualizar(id, produtoAtualizado);

    assertEquals("Mouse Gamer", resultado.getNome());
    verify(repository, times(1)).save(produtoExistente);
  }
}