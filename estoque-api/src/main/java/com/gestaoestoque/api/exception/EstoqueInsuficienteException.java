package com.gestaoestoque.api.exception;

public class EstoqueInsuficienteException extends RuntimeException {

  public EstoqueInsuficienteException(String nomeProduto, int disponivel, int solicitado) {
    super(String.format(
      "Estoque insuficiente para o produto '%s'. Disponível: %d | Solicitado: %d",
      nomeProduto, disponivel, solicitado
    ));
  }
}
