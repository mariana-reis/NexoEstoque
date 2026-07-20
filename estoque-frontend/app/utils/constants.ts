import { TIPO_PRODUTO_LABELS, type TipoProduto } from "~/types/produto";

export const TIPO_PRODUTO_OPTIONS = Object.entries(TIPO_PRODUTO_LABELS).map(
  ([value, label]) => ({
    label,
    value: value as TipoProduto,
  }),
);
