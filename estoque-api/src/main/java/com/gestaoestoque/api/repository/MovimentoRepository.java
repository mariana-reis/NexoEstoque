package com.gestaoestoque.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gestaoestoque.api.domain.MovimentoEstoque;
import com.gestaoestoque.api.domain.TipoMovimentacao;
import com.gestaoestoque.api.domain.TipoProduto;
import com.gestaoestoque.api.dto.LucroProdutoDTO;

public interface MovimentoRepository extends JpaRepository<MovimentoEstoque, Long>{
  
  @Query(
    value = "SELECT m FROM MovimentoEstoque m JOIN FETCH m.produto",
    countQuery = "SELECT count(m) FROM MovimentoEstoque m"
  )
  Page<MovimentoEstoque> findAll(Pageable pageable);
  
  @Query(
    value = "SELECT m FROM MovimentoEstoque m JOIN FETCH m.produto WHERE m.tipo = :tipo",
    countQuery = "SELECT count(m) FROM MovimentoEstoque m WHERE m.tipo = :tipo"
  )
  Page<MovimentoEstoque> findByTipo(@Param("tipo") TipoMovimentacao tipo, Pageable pageable);
  
  List<MovimentoEstoque> findByProdutoId(Long produtoId);
  
  
  @Query("""
    SELECT new com.gestaoestoque.api.dto.LucroProdutoDTO(
      p.id,
      p.nome,
      SUM(m.quantidade),
      p.valorFornecedor,
      SUM(m.valorVenda * m.quantidade) / SUM(m.quantidade),
      (SUM(m.valorVenda * m.quantidade) / SUM(m.quantidade)) - p.valorFornecedor,
      SUM((m.valorVenda - p.valorFornecedor) * m.quantidade)
    )
    FROM MovimentoEstoque m
    JOIN m.produto p
    WHERE m.tipo = com.gestaoestoque.api.domain.TipoMovimentacao.SAIDA
    GROUP BY p.id, p.nome, p.valorFornecedor
    ORDER BY SUM((m.valorVenda - p.valorFornecedor) * m.quantidade) DESC
    """)
  List<LucroProdutoDTO> calcularLucroPorProduto();
  
  @Query("""
    SELECT COALESCE(SUM(m.quantidade), 0)
    FROM MovimentoEstoque m
    JOIN m.produto p
    WHERE m.tipo = com.gestaoestoque.api.domain.TipoMovimentacao.SAIDA
      AND p.tipo = :tipo
    """)
  long somarSaidaPorTipoProduto(@Param("tipo") TipoProduto tipo);
}
