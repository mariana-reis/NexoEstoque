import type { Produto } from "./produto";

export type TipoMovimentacao = "ENTRADA" | "SAIDA";

export const TIPO_MOVIMENTACAO_LABELS: Record<TipoMovimentacao, string> = {
  ENTRADA: "Entrada",
  SAIDA: "Saída",
};

export const TIPO_MOVIMENTACAO_COLORS: Record<
  TipoMovimentacao,
  "success" | "error"
> = {
  ENTRADA: "success",
  SAIDA: "error",
};

export const TIPO_MOVIMENTACAO_ICONS: Record<TipoMovimentacao, string> = {
  ENTRADA: "i-lucide-arrow-down-to-line",
  SAIDA: "i-lucide-arrow-up-from-line",
};

export interface MovimentoEstoque {
  id: number;
  produto: Produto;
  tipo: TipoMovimentacao;
  valorVenda: number;
  dataMovimentacao: string;
  quantidade: number;
}

export interface MovimentoForm {
  produto: { id: number };
  tipo: TipoMovimentacao;
  valorVenda: number;
  dataMovimentacao: string;
  quantidade: number;
}
