package com.gestaoestoque.api.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestaoestoque.api.domain.MovimentoEstoque;
import com.gestaoestoque.api.domain.Produto;
import com.gestaoestoque.api.domain.TipoMovimentacao;
import com.gestaoestoque.api.dto.LucroProdutoDTO;
import com.gestaoestoque.api.exception.EstoqueInsuficienteException;
import com.gestaoestoque.api.exception.ResourceNotFoundException;
import com.gestaoestoque.api.repository.MovimentoRepository;
import com.gestaoestoque.api.repository.ProdutoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimentoService {

  private final MovimentoRepository movimentoRepository;
  private final ProdutoRepository produtoRepository;
  
  @Transactional
  public MovimentoEstoque registrar(MovimentoEstoque movimento) {
    Produto produto = produtoRepository.findById(movimento.getProduto().getId())
        .orElseThrow(() -> new ResourceNotFoundException(
          "Produto não encontrado com ID: " + movimento.getProduto().getId()
        ));

    movimento.setProduto(produto);

    if (movimento.getTipo() == TipoMovimentacao.SAIDA) {
      int disponivel = produto.getQuantidadeEstoque();
      int solicitado = movimento.getQuantidade();

      if (solicitado > disponivel) {
        throw new EstoqueInsuficienteException(produto.getNome(), disponivel, solicitado);
      }

      produto.setQuantidadeEstoque(disponivel - solicitado);

    } else {
      produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + movimento.getQuantidade());
    }

    produtoRepository.save(produto);

    return movimentoRepository.save(movimento);
  }
  
  @Transactional(readOnly = true)
  public Page<MovimentoEstoque> listar(Pageable pageable) {
    return movimentoRepository.findAll(pageable);
  }
  
  @Transactional(readOnly = true)
  public MovimentoEstoque buscarPorId(Long id) {
    return movimentoRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(
        "Movimentação não encontrada com ID: " + id
      ));
  }
  
  @Transactional(readOnly = true)
  public List<LucroProdutoDTO> consultarLucro() {
    return movimentoRepository.calcularLucroPorProduto();
  }
}
