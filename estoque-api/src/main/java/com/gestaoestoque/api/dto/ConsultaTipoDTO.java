package com.gestaoestoque.api.dto;

import com.gestaoestoque.api.domain.TipoProduto;

public record ConsultaTipoDTO(
  TipoProduto tipo,
  long totalProdutos,
  long quantidadeDisponivel,
  long quantidadeSaida
) {}
