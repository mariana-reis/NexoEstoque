package com.gestaoestoque.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gestaoestoque.api.domain.Produto;
import com.gestaoestoque.api.domain.TipoProduto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
  Page<Produto> findByTipo(TipoProduto tipo, Pageable pageable);
  Page<Produto> findByQuantidadeEstoqueLessThan(Integer quantidade, Pageable pageable);
}
