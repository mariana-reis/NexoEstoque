import { defineStore } from "pinia";
import type { Produto, TipoProduto } from "~/types/produto";
import type { ConsultaTipoDTO, PaginationParams } from "~/types/api";
import { useProdutos } from "~/composables/useProdutos";

export const useProdutoStore = defineStore("produto", () => {
  const {
    buscarProdutos,
    consultarPorTipo: apiConsultarPorTipo,
    excluirProduto,
  } = useProdutos();

  const produtos = ref<Produto[]>([]);
  const totalElements = ref(0);
  const isCarregando = ref(false);
  const dashboardStats = ref<ConsultaTipoDTO[]>([]);

  const estoqueBaixo = computed(() => {
    return produtos.value.filter((p) => p.quantidadeEstoque < 5);
  });

  async function carregarLista(params: PaginationParams, tipo?: TipoProduto) {
    isCarregando.value = true;
    try {
      const response = await buscarProdutos(params, tipo);
      produtos.value = response.content;
      totalElements.value = response.totalElements;
    } finally {
      isCarregando.value = false;
    }
  }

  async function carregarDashboard() {
    isCarregando.value = true;
    try {
      const response = await apiConsultarPorTipo();
      dashboardStats.value = response;
    } finally {
      isCarregando.value = false;
    }
  }

  async function remover(id: number) {
    await excluirProduto(id);
    produtos.value = produtos.value.filter((p) => p.id !== id);
    totalElements.value--;
  }

  return {
    produtos,
    totalElements,
    isCarregando,
    dashboardStats,
    estoqueBaixo,
    carregarLista,
    carregarDashboard,
    remover,
  };
});
