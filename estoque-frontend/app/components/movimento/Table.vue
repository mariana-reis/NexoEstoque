<script setup lang="ts">
import {
  TIPO_MOVIMENTACAO_COLORS,
  TIPO_MOVIMENTACAO_LABELS,
  type MovimentoEstoque,
} from "~/types/movimento";
import { formatCurrency, formatDate, formatNumber } from "~/utils/formatters";

const props = defineProps<{
  movimentos: MovimentoEstoque[];
  loading?: boolean;
  total: number;
  page: number;
  pageSize: number;
}>();

const emit = defineEmits<{
  (e: "update:page", page: number): void;
}>();

const internalPage = computed({
  get: () => (props.page ?? 0) + 1,
  set: (val: number) => emit("update:page", val - 1),
});

const table = useTemplateRef("table");

const columns = [
  { accessorKey: "id", header: "ID" },
  { accessorKey: "dataMovimentacao", header: "Data" },
  { id: "produtoNome", accessorKey: "produto.nome", header: "Produto" },
  { accessorKey: "tipo", header: "Tipo" },
  { accessorKey: "quantidade", header: "Qtd" },
  { accessorKey: "valorVenda", header: "Valor de Venda" },
];
</script>

<template>
  <div class="space-y-4">
    <div class="flex items-center gap-2">
      <UInput
        :model-value="
          table?.tableApi?.getColumn('produtoNome')?.getFilterValue() as string
        "
        class="max-w-sm w-full"
        placeholder="Buscar por produto..."
        icon="i-lucide-search"
        @update:model-value="
          table?.tableApi?.getColumn('produtoNome')?.setFilterValue($event)
        "
      />
    </div>

    <UTable
      ref="table"
      :data="movimentos"
      :columns="columns"
      :loading="loading"
    >
      <template #empty>
        <UEmpty icon="i-lucide-trending-up-down" title="Nenhuma movimentação encontrada." />
      </template>
      <template #dataMovimentacao-cell="{ row }">
        {{ formatDate(row.original.dataMovimentacao) }}
      </template>

      <template #tipo-cell="{ row }">
        <UBadge
          :color="TIPO_MOVIMENTACAO_COLORS[row.original.tipo]"
          variant="subtle"
        >
          <UIcon
            :name="
              row.original.tipo === 'ENTRADA'
                ? 'i-lucide-arrow-down-to-line'
                : 'i-lucide-arrow-up-from-line'
            "
            class="mr-1 w-3 h-3"
          />
          {{ TIPO_MOVIMENTACAO_LABELS[row.original.tipo] }}
        </UBadge>
      </template>

      <template #quantidade-cell="{ row }">
        <span
          class="font-medium"
          :class="
            row.original.tipo === 'ENTRADA'
              ? 'text-green-600 dark:text-green-400'
              : 'text-red-600 dark:text-red-400'
          "
        >
          {{ row.original.tipo === "ENTRADA" ? "+" : "-"
          }}{{ formatNumber(row.original.quantidade) }}
        </span>
      </template>

      <template #valorVenda-cell="{ row }">
        <span v-if="row.original.tipo === 'SAIDA'">
          {{ formatCurrency(row.original.valorVenda) }}
        </span>
        <span v-else class="text-gray-400">-</span>
      </template>
    </UTable>

    <div
      class="flex justify-between items-center pt-4 border-t border-gray-200 dark:border-gray-800"
    >
      <div class="text-sm text-gray-500">
        Total:
        <span class="font-medium text-gray-900 dark:text-white">{{
          total
        }}</span>
        registros
      </div>
      <UPagination
        v-model:page="internalPage"
        :total="total"
        :items-per-page="pageSize"
      />
    </div>
  </div>
</template>
