export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
  empty: boolean;
}

export interface ApiError {
  timestamp: string;
  status: number;
  error: string;
  message: string;
  path: string;
  details?: Record<string, string>;
}

export interface ConsultaTipoDTO {
  tipo: "ELETRONICO" | "ELETRODOMESTICO" | "MOVEL";
  totalProdutos: number;
  quantidadeDisponivel: number;
  quantidadeSaida: number;
}

export interface LucroProdutoDTO {
  produtoId: number;
  nomeProduto: string;
  totalSaida: number;
  valorFornecedor: number;
  valorMedioVenda: number;
  lucroUnitario: number;
  lucroTotal: number;
}

export interface PaginationParams {
  page: number;
  size: number;
  sort?: string;
}
