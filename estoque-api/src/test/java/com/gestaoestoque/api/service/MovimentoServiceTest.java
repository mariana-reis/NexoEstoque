package com.gestaoestoque.api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gestaoestoque.api.domain.MovimentoEstoque;
import com.gestaoestoque.api.domain.Produto;
import com.gestaoestoque.api.domain.TipoMovimentacao;
import com.gestaoestoque.api.repository.MovimentoRepository;
import com.gestaoestoque.api.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
public class MovimentoServiceTest {
  
  @Mock
  private MovimentoRepository movimentoRepository;

  @Mock
  private ProdutoRepository produtoRepository;

  @InjectMocks
  private MovimentoService movimentoService;
  
  @Test
  @DisplayName("Deve registrar entrada com sucesso e somar ao estoque do produto")
  void registrarEntrada_Sucesso() {
    Produto produtoMock = new Produto();
    produtoMock.setId(1L);
    produtoMock.setNome("Monitor");
    produtoMock.setQuantidadeEstoque(10);
    
    MovimentoEstoque movimento = new MovimentoEstoque();
    movimento.setProduto(produtoMock);
    movimento.setTipo(TipoMovimentacao.ENTRADA);
    movimento.setQuantidade(5);
    movimento.setValorVenda(BigDecimal.ZERO);
    movimento.setDataMovimentacao(LocalDate.now());

    when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoMock));
    when(movimentoRepository.save(any(MovimentoEstoque.class))).thenReturn(movimento);

    MovimentoEstoque resultado = movimentoService.registrar(movimento);

    assertNotNull(resultado);
    assertEquals(15, produtoMock.getQuantidadeEstoque(), "O estoque não foi somado corretamente");
    verify(produtoRepository, times(1)).save(produtoMock);
    verify(movimentoRepository, times(1)).save(movimento);
  }

}
