package com.gestaoestoque.api.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @NotBlank(message = "O nome do produto é obrigatório")
  @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
  @Column(nullable = false, length = 100)
  private String nome;
  
  @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
  @Column(length = 255)
  private String descricao;
  
  @NotNull(message = "O tipo do produto é obrigatório")
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TipoProduto tipo;
  
  @NotNull(message = "O valor no fornecedor é obrigatório")
  @DecimalMin(value = "0.01", message = "O valor no fornecedor deve ser maior que zero")
  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal valorFornecedor;
  
  @NotNull(message = "A quantidade em estoque é obrigatória")
  @Min(value = 0, message = "A quantidade em estoque não pode ser negativa")
  @Column(nullable = false)
  private Integer quantidadeEstoque;
}