export type TipoProduto = "ELETRONICO" | "ELETRODOMESTICO" | "MOVEL";

export const TIPO_PRODUTO_LABELS: Record<TipoProduto, string> = {
  ELETRONICO: "Eletrônico",
  ELETRODOMESTICO: "Eletrodoméstico",
  MOVEL: "Móvel",
};

export type NuxtUIColor =
  | "primary"
  | "secondary"
  | "success"
  | "info"
  | "warning"
  | "error"
  | "neutral";

export const TIPO_PRODUTO_COLORS: Record<TipoProduto, NuxtUIColor> = {
  ELETRONICO: "info",
  ELETRODOMESTICO: "warning",
  MOVEL: "success",
};

export const TIPO_PRODUTO_ICONS: Record<TipoProduto, string> = {
  ELETRONICO: "i-lucide-cpu",
  ELETRODOMESTICO: "i-lucide-plug",
  MOVEL: "i-lucide-sofa",
};

export interface Produto {
  id: number;
  nome: string;
  descricao?: string;
  tipo: TipoProduto;
  valorFornecedor: number;
  quantidadeEstoque: number;
}

export type ProdutoForm = Omit<Produto, "id">;
