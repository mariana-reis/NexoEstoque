package com.gestaoestoque.api.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


@Entity
@Table(name = "tb_movimento_estoque")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimentoEstoque {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @NotNull(message = "O produto é obrigatório")
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "produto_id", nullable = false)
  private Produto produto;
  
  @NotNull(message = "O tipo de movimentação é obrigatório")
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TipoMovimentacao tipo;
  
  @NotNull(message = "O valor de venda é obrigatório")
  @DecimalMin(value = "0.00", message = "O valor de venda não pode ser negativo")
  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal valorVenda;
  
  @NotNull(message = "A data da movimentação é obrigatória")
  @Column(nullable = false)
  private LocalDate dataMovimentacao;
  
  @NotNull(message = "A quantidade é obrigatória")
  @Min(value = 1, message = "A quantidade deve ser no mínimo 1")
  @Column(nullable = false)
  private Integer quantidade;
}
