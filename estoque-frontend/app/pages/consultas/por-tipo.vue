<script setup lang="ts">
import { useProdutoStore } from "~/stores/produto";
import {
  TIPO_PRODUTO_COLORS,
  TIPO_PRODUTO_LABELS,
  TIPO_PRODUTO_ICONS,
} from "~/types/produto";
import { formatNumber } from "~/utils/formatters";

const store = useProdutoStore();

onMounted(async () => {
  await store.carregarDashboard();
});

const columns = [
  { accessorKey: "tipo", header: "Tipo de Produto" },
  { accessorKey: "totalProdutos", header: "Produtos Cadastrados" },
  { accessorKey: "quantidadeDisponivel", header: "Em Estoque (Disponível)" },
  { accessorKey: "quantidadeSaida", header: "Total Vendido (Saída)" },
];

function getColorClasses(color: string) {
  const map: Record<string, string> = {
    info: "bg-blue-100 dark:bg-blue-900/30 text-blue-600 dark:text-blue-400",
    warning:
      "bg-amber-100 dark:bg-amber-900/30 text-amber-600 dark:text-amber-400",
    success:
      "bg-green-100 dark:bg-green-900/30 text-green-600 dark:text-green-400",
  };
  return (
    map[color] ||
    "bg-gray-100 dark:bg-gray-800 text-gray-600 dark:text-gray-400"
  );
}
</script>

<template>
  <div class="space-y-6">
    <div>
      <h2
        class="text-2xl font-bold tracking-tight text-gray-900 dark:text-white"
      >
        Consulta por Tipo de Produto
      </h2>
      <p class="text-sm text-gray-500 dark:text-gray-400">
        Visão agregada de estoque disponível e saídas por categoria.
      </p>
    </div>

    <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
      <UCard
        v-for="stat in store.dashboardStats"
        :key="stat.tipo"
        class="relative overflow-hidden"
      >
        <div class="flex items-center gap-4 mb-4">
          <div
            class="p-3 rounded-lg"
            :class="getColorClasses(TIPO_PRODUTO_COLORS[stat.tipo])"
          >
            <UIcon :name="TIPO_PRODUTO_ICONS[stat.tipo]" class="w-6 h-6" />
          </div>
          <div>
            <h3 class="text-lg font-bold text-gray-900 dark:text-white">
              {{ TIPO_PRODUTO_LABELS[stat.tipo] }}
            </h3>
            <p class="text-sm text-gray-500">
              {{ stat.totalProdutos }} produtos
            </p>
          </div>
        </div>

        <div
          class="grid grid-cols-2 gap-4 pt-4 border-t border-gray-100 dark:border-gray-800"
        >
          <div>
            <p
              class="text-xs text-gray-500 uppercase font-semibold tracking-wider"
            >
              Disponível
            </p>
            <p
              class="text-2xl font-bold text-primary-600 dark:text-primary-400"
            >
              {{ formatNumber(stat.quantidadeDisponivel) }}
            </p>
          </div>
          <div>
            <p
              class="text-xs text-gray-500 uppercase font-semibold tracking-wider"
            >
              Saídas
            </p>
            <p class="text-2xl font-bold text-gray-900 dark:text-white">
              {{ formatNumber(stat.quantidadeSaida) }}
            </p>
          </div>
        </div>
      </UCard>
    </div>

    <UCard>
      <template #header>
        <div>
          <h3 class="font-semibold text-gray-900 dark:text-white">
            Resumo por Categoria
          </h3>
          <p class="mt-1 text-sm text-gray-500 dark:text-gray-400">
            Comparativo consolidado dos produtos cadastrados, estoque disponível
            e movimentações por tipo.
          </p>
        </div>
      </template>

      <UTable
        :data="store.dashboardStats"
        :columns="columns"
        :loading="store.isCarregando"
      >
        <template #empty>
          <UEmpty icon="i-lucide-package" title="Nenhum dado encontrado." />
        </template>
        <template #tipo-cell="{ row }">
          <UBadge
            :color="TIPO_PRODUTO_COLORS[row.original.tipo]"
            variant="subtle"
          >
            <UIcon
              :name="TIPO_PRODUTO_ICONS[row.original.tipo]"
              class="mr-2 w-4 h-4"
            />
            {{ TIPO_PRODUTO_LABELS[row.original.tipo] }}
          </UBadge>
        </template>

        <template #totalProdutos-cell="{ row }">
          {{ formatNumber(row.original.totalProdutos) }}
        </template>

        <template #quantidadeDisponivel-cell="{ row }">
          <span class="font-semibold text-primary-600 dark:text-primary-400">
            {{ formatNumber(row.original.quantidadeDisponivel) }}
          </span>
        </template>

        <template #quantidadeSaida-cell="{ row }">
          <span class="font-semibold text-gray-900 dark:text-gray-100">
            {{ formatNumber(row.original.quantidadeSaida) }}
          </span>
        </template>
      </UTable>
    </UCard>
  </div>
</template>
