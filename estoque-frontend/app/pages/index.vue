<script setup lang="ts">
import { useMovimentoStore } from "~/stores/movimento";
import { useProdutoStore } from "~/stores/produto";
import { TIPO_PRODUTO_COLORS, TIPO_PRODUTO_LABELS } from "~/types/produto";
import { formatCurrency } from "~/utils/formatters";

const produtoStore = useProdutoStore();
const movimentoStore = useMovimentoStore();

onMounted(async () => {
  try {
    await Promise.all([
      produtoStore.carregarLista({ page: 0, size: 100 }),
      produtoStore.carregarDashboard(),
      movimentoStore.carregarRankingLucro(),
      movimentoStore.carregarLista({
        page: 0,
        size: 5,
        sort: "dataMovimentacao,desc",
      }),
    ]);
  } catch (error) {
    console.error("Erro ao carregar dashboard", error);
  }
});

const totalProdutosCadastrados = computed(() => {
  return produtoStore.dashboardStats.reduce(
    (acc, stat) => acc + stat.totalProdutos,
    0,
  );
});

const valorTotalEstoque = computed(() => {
  return produtoStore.produtos.reduce(
    (acc, p) => acc + p.valorFornecedor * p.quantidadeEstoque,
    0,
  );
});

const totalEstoqueDisponivel = computed(() => {
  return produtoStore.dashboardStats.reduce(
    (acc, stat) => acc + stat.quantidadeDisponivel,
    0,
  );
});

const columnsEstoqueBaixo = [
  { accessorKey: "id", header: "ID" },
  { accessorKey: "nome", header: "Produto" },
  { accessorKey: "tipo", header: "Tipo" },
  { accessorKey: "quantidadeEstoque", header: "Em Estoque" },
];

const columnsMovimentos = [
  { accessorKey: "produto.nome", header: "Produto" },
  { accessorKey: "tipo", header: "Movimento" },
  { accessorKey: "quantidade", header: "Quantidade" },
  { accessorKey: "dataMovimentacao", header: "Data" },
];
</script>

<template>
  <div class="space-y-6">
    <div
      class="flex flex-col gap-2 md:flex-row md:items-end md:justify-between"
    >
      <div>
        <h2
          class="text-2xl font-bold tracking-tight text-gray-900 dark:text-white"
        >
          Visão Geral do Estoque
        </h2>
        <p class="text-sm text-gray-500 dark:text-gray-400">
          Acompanhe os principais indicadores do seu negócio.
        </p>
      </div>
      <UButton
        label="Novo Produto"
        to="/produtos/novo"
        icon="i-lucide-plus"
        color="primary"
      />
    </div>

    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
      <DashboardStatsCard
        title="Total de Produtos"
        :value="totalProdutosCadastrados"
        icon="i-lucide-package"
        color="info"
        description="itens cadastrados"
      />
      <DashboardStatsCard
        title="Total em Estoque"
        :value="totalEstoqueDisponivel"
        icon="i-lucide-layers"
        color="primary"
        description="unidades disponíveis"
      />
      <DashboardStatsCard
        title="Valor do Estoque"
        :value="formatCurrency(valorTotalEstoque)"
        icon="i-lucide-dollar-sign"
        color="warning"
        description="a preço de custo"
      />
      <DashboardStatsCard
        title="Lucro Acumulado"
        :value="formatCurrency(movimentoStore.lucroTotalAcumulado)"
        icon="i-lucide-trending-up"
        color="success"
        description="baseado nas vendas"
      />
    </div>

    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
      <UCard>
        <template #header>
          <div class="flex items-center gap-2 text-error-500 font-semibold">
            <UIcon name="i-lucide-alert-triangle" class="w-5 h-5" />
            <h3>Alerta de Estoque Baixo</h3>
          </div>
        </template>

        <UTable
          :data="produtoStore.estoqueBaixo"
          :columns="columnsEstoqueBaixo"
          :loading="produtoStore.isCarregando"
        >
          <template #empty>
            <UEmpty
              icon="i-lucide-package"
              title="Nenhum produto com estoque baixo."
            />
          </template>
          <template #tipo-cell="{ row }">
            <UBadge
              :color="TIPO_PRODUTO_COLORS[row.original.tipo]"
              variant="subtle"
            >
              {{ TIPO_PRODUTO_LABELS[row.original.tipo] }}
            </UBadge>
          </template>
          <template #quantidadeEstoque-cell="{ row }">
            <span class="text-error-600 dark:text-error-400 font-bold">
              {{ row.original.quantidadeEstoque }}
            </span>
          </template>
        </UTable>
      </UCard>

      <UCard>
        <template #header>
          <div class="flex items-center justify-between">
            <h3 class="font-semibold text-gray-900 dark:text-white">
              Últimas Movimentações
            </h3>
            <UButton to="/movimentos" color="neutral" variant="ghost" size="sm">
              Ver tudo
            </UButton>
          </div>
        </template>

        <UTable
          :data="movimentoStore.ultimasMovimentacoes"
          :columns="columnsMovimentos"
          :loading="movimentoStore.isCarregando"
        >
          <template #empty>
            <UEmpty
              icon="i-lucide-trending-up-down"
              title="Nenhuma movimentação recente."
            />
          </template>
          <template #tipo-cell="{ row }">
            <UBadge
              :color="row.original.tipo === 'ENTRADA' ? 'success' : 'error'"
              variant="subtle"
            >
              {{ row.original.tipo === "ENTRADA" ? "Entrada" : "Saída" }}
            </UBadge>
          </template>
          <template #dataMovimentacao-cell="{ row }">
            {{ formatDate(row.original.dataMovimentacao) }}
          </template>
          <template #quantidade-cell="{ row }">
            <span
              :class="
                row.original.tipo === 'ENTRADA'
                  ? 'text-green-600'
                  : 'text-red-600'
              "
            >
              {{ row.original.tipo === "ENTRADA" ? "+" : "-"
              }}{{ row.original.quantidade }}
            </span>
          </template>
        </UTable>
      </UCard>
    </div>
  </div>
</template>
