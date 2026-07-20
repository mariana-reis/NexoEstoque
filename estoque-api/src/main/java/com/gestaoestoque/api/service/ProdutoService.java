package com.gestaoestoque.api.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestaoestoque.api.domain.Produto;
import com.gestaoestoque.api.domain.TipoProduto;
import com.gestaoestoque.api.dto.ConsultaTipoDTO;
import com.gestaoestoque.api.exception.ResourceNotFoundException;
import com.gestaoestoque.api.repository.MovimentoRepository;
import com.gestaoestoque.api.repository.ProdutoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoService {
  
  private final ProdutoRepository produtoRepository;
  private final MovimentoRepository movimentoRepository;
  
  @Transactional
  public Produto salvar(Produto produto) {
    return produtoRepository.save(produto);
  }
  
  @Transactional(readOnly = true)
  public Page<Produto> listarProdutos(Pageable pageable) {
    return produtoRepository.findAll(pageable);
  }
  
  @Transactional(readOnly = true)
  public Produto buscarPorId(Long id) {
    return produtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
  }

  @Transactional
  public Produto atualizar(Long id, Produto produtoAtualizado) {
    Produto produtoExistente = buscarPorId(id);
    
    produtoExistente.setNome(produtoAtualizado.getNome());
    produtoExistente.setDescricao(produtoAtualizado.getDescricao());
    produtoExistente.setTipo(produtoAtualizado.getTipo());
    produtoExistente.setValorFornecedor(produtoAtualizado.getValorFornecedor());
    produtoExistente.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());
    
    return produtoRepository.save(produtoExistente);
  }
  
  @Transactional
  public void deletar(Long id) {
    if (!produtoRepository.existsById(id)) {
      throw new ResourceNotFoundException("Não foi possível excluir. Produto não encontrado com o ID: " + id);
    }
    produtoRepository.deleteById(id);
  }
  
  @Transactional(readOnly = true)
  public Page<Produto> buscarPorTipo(TipoProduto tipo, Pageable pageable) {
    return produtoRepository.findByTipo(tipo, pageable);
  }
  
  @Transactional(readOnly = true)
  public List<ConsultaTipoDTO> consultarPorTipo() {
    return Arrays.stream(TipoProduto.values()).map(tipo -> {
      List<Produto> produtos = produtoRepository.findByTipo(tipo, Pageable.unpaged()).getContent();
      long totalProdutos = produtos.size();
      long quantidadeDisponivel = produtos.stream().mapToLong(p -> p.getQuantidadeEstoque()).sum();
      long quantidadeSaida = movimentoRepository.somarSaidaPorTipoProduto(tipo);
      return new ConsultaTipoDTO(tipo, totalProdutos, quantidadeDisponivel, quantidadeSaida);
    })
    .toList();
  }
  
  
}
