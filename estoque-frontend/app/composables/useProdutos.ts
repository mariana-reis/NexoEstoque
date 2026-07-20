import type {
  PageResponse,
  PaginationParams,
  ConsultaTipoDTO,
} from "~/types/api";
import type { Produto, ProdutoForm, TipoProduto } from "~/types/produto";
import { useApi } from "./useApi";

export function useProdutos() {
  const { fetchApi } = useApi();

  async function buscarProdutos(params: PaginationParams, tipo?: TipoProduto) {
    const query: Record<string, any> = { ...params };
    if (tipo) {
      query.tipo = tipo;
    }

    return await fetchApi<PageResponse<Produto>>("/api/produtos", {
      method: "GET",
      query,
    });
  }

  async function buscarProduto(id: number) {
    return await fetchApi<Produto>(`/api/produtos/${id}`, {
      method: "GET",
    });
  }

  async function criarProduto(produto: ProdutoForm) {
    return await fetchApi<Produto>("/api/produtos", {
      method: "POST",
      body: produto,
    });
  }

  async function atualizarProduto(id: number, produto: ProdutoForm) {
    return await fetchApi<Produto>(`/api/produtos/${id}`, {
      method: "PUT",
      body: produto,
    });
  }

  async function excluirProduto(id: number) {
    return await fetchApi<void>(`/api/produtos/${id}`, {
      method: "DELETE",
    });
  }

  async function consultarPorTipo() {
    return await fetchApi<ConsultaTipoDTO[]>("/api/produtos/por-tipo", {
      method: "GET",
    });
  }

  return {
    buscarProdutos,
    buscarProduto,
    criarProduto,
    atualizarProduto,
    excluirProduto,
    consultarPorTipo,
  };
}
