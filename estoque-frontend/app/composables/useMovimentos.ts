import type {
  PageResponse,
  PaginationParams,
  LucroProdutoDTO,
} from "~/types/api";
import type { MovimentoEstoque, MovimentoForm } from "~/types/movimento";
import { useApi } from "./useApi";

export function useMovimentos() {
  const { fetchApi } = useApi();

  async function buscarMovimentos(params: PaginationParams) {
    return await fetchApi<PageResponse<MovimentoEstoque>>("/api/movimentos", {
      method: "GET",
      query: params,
    });
  }

  async function registrarMovimentacao(movimento: MovimentoForm) {
    return await fetchApi<MovimentoEstoque>("/api/movimentos", {
      method: "POST",
      body: movimento,
    });
  }

  async function consultarLucro() {
    return await fetchApi<LucroProdutoDTO[]>("/api/movimentos/lucro", {
      method: "GET",
    });
  }

  return {
    buscarMovimentos,
    registrarMovimentacao,
    consultarLucro,
  };
}
