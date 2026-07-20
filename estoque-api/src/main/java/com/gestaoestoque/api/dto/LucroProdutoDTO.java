package com.gestaoestoque.api.dto;

import java.math.BigDecimal;

public record LucroProdutoDTO(
  Long produtoId,
  String nomeProduto,
  long totalSaida,
  BigDecimal valorFornecedor,
  BigDecimal valorMedioVenda,
  BigDecimal lucroUnitario,
  BigDecimal lucroTotal
) {}
