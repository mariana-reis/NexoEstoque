import { defineStore } from "pinia";
import type { MovimentoEstoque } from "~/types/movimento";
import type { LucroProdutoDTO, PaginationParams } from "~/types/api";
import { useMovimentos } from "~/composables/useMovimentos";

export const useMovimentoStore = defineStore("movimento", () => {
  const { buscarMovimentos, consultarLucro: apiConsultarLucro } =
    useMovimentos();

  const movimentos = ref<MovimentoEstoque[]>([]);
  const totalElements = ref(0);
  const isCarregando = ref(false);
  const rankingLucro = ref<LucroProdutoDTO[]>([]);

  const ultimasMovimentacoes = computed(() => {
    return movimentos.value.slice(0, 5);
  });

  const lucroTotalAcumulado = computed(() => {
    return rankingLucro.value.reduce((acc, item) => acc + item.lucroTotal, 0);
  });

  async function carregarLista(params: PaginationParams) {
    isCarregando.value = true;
    try {
      const response = await buscarMovimentos(params);
      movimentos.value = response.content;
      totalElements.value = response.totalElements;
    } finally {
      isCarregando.value = false;
    }
  }

  async function carregarRankingLucro() {
    isCarregando.value = true;
    try {
      const response = await apiConsultarLucro();
      rankingLucro.value = response;
    } finally {
      isCarregando.value = false;
    }
  }

  return {
    movimentos,
    totalElements,
    isCarregando,
    rankingLucro,
    ultimasMovimentacoes,
    lucroTotalAcumulado,
    carregarLista,
    carregarRankingLucro,
  };
});
