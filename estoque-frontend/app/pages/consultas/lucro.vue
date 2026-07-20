<script setup lang="ts">
import { useMovimentoStore } from "~/stores/movimento";
import { formatCurrency, formatNumber } from "~/utils/formatters";

const store = useMovimentoStore();

onMounted(async () => {
  await store.carregarRankingLucro();
});

const columns = [
  { accessorKey: "nomeProduto", header: "Produto" },
  { accessorKey: "totalSaida", header: "Total Vendido" },
  { accessorKey: "valorFornecedor", header: "Custo Unitário" },
  { accessorKey: "valorMedioVenda", header: "Preço Médio Venda" },
  { accessorKey: "lucroUnitario", header: "Lucro por Unidade" },
  { accessorKey: "lucroTotal", header: "Lucro Total" },
];
</script>

<template>
  <div class="space-y-6">
    <div
      class="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4"
    >
      <div>
        <h2
          class="text-2xl font-bold tracking-tight text-gray-900 dark:text-white"
        >
          Análise de Lucratividade
        </h2>
        <p class="text-sm text-gray-500 dark:text-gray-400">
          Acompanhe quais produtos estão gerando mais retorno para o negócio.
        </p>
      </div>

      <div
        class="bg-primary-50 dark:bg-primary-950/50 border border-primary-200 dark:border-primary-900 rounded-xl px-6 py-3 text-right"
      >
        <p class="text-sm text-primary-600 dark:text-primary-400 font-medium">
          Lucro Total Acumulado
        </p>
        <p class="text-2xl font-bold text-primary-700 dark:text-primary-300">
          {{ formatCurrency(store.lucroTotalAcumulado) }}
        </p>
      </div>
    </div>

    <UCard>
      <template #header>
        <div class="flex items-center gap-2">
          <UIcon name="i-lucide-award" class="w-5 h-5 text-amber-500" />
          <h3 class="font-semibold text-gray-900 dark:text-white">
            Ranking de Lucro por Produto
          </h3>
        </div>
      </template>

      <UTable
        :data="store.rankingLucro"
        :columns="columns"
        :loading="store.isCarregando"
      >
        <template #empty>
          <UEmpty
            icon="i-lucide-trending-up"
            title="Nenhum dado de lucro disponível. Registre vendas para visualizar o
            relatório.."
          />
        </template>
        <template #nomeProduto-cell="{ row }">
          <div class="flex items-center gap-3">
            <span
              class="flex items-center justify-center w-6 h-6 rounded-full text-xs font-bold"
              :class="
                row.index === 0
                  ? 'bg-amber-100 text-amber-700 dark:bg-amber-900/50 dark:text-amber-400'
                  : 'bg-gray-100 text-gray-500 dark:bg-gray-800'
              "
            >
              {{ row.index + 1 }}
            </span>
            <span class="font-medium text-gray-900 dark:text-white">{{
              row.original.nomeProduto
            }}</span>
          </div>
        </template>

        <template #totalSaida-cell="{ row }">
          {{ formatNumber(row.original.totalSaida) }} un
        </template>

        <template #valorFornecedor-cell="{ row }">
          {{ formatCurrency(row.original.valorFornecedor) }}
        </template>

        <template #valorMedioVenda-cell="{ row }">
          {{ formatCurrency(row.original.valorMedioVenda) }}
        </template>

        <template #lucroUnitario-cell="{ row }">
          <span
            :class="
              row.original.lucroUnitario >= 0
                ? 'text-green-600 dark:text-green-400'
                : 'text-red-600 dark:text-red-400'
            "
          >
            {{ formatCurrency(row.original.lucroUnitario) }}
          </span>
        </template>

        <template #lucroTotal-cell="{ row }">
          <span
            class="font-bold"
            :class="
              row.original.lucroTotal >= 0
                ? 'text-green-600 dark:text-green-400'
                : 'text-red-600 dark:text-red-400'
            "
          >
            {{ formatCurrency(row.original.lucroTotal) }}
          </span>
        </template>
      </UTable>
    </UCard>
  </div>
</template>
