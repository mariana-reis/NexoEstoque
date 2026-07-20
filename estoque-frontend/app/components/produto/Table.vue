<script setup lang="ts">
import {
  TIPO_PRODUTO_COLORS,
  TIPO_PRODUTO_LABELS,
  type Produto,
} from "~/types/produto";
import { formatCurrency, formatNumber } from "~/utils/formatters";

const props = defineProps<{
  produtos: Produto[];
  loading?: boolean;
  total: number;
  page: number;
  pageSize: number;
}>();

const emit = defineEmits<{
  (e: "update:page", page: number): void;
  (e: "delete", id: number): void;
  (e: "view", produto: Produto): void;
}>();

const internalPage = computed({
  get: () => (props.page ?? 0) + 1,
  set: (val: number) => emit("update:page", val - 1),
});

function getRowItems(row: any) {
  return [
    {
      label: "Detalhes",
      icon: "i-lucide-eye",
      onSelect: () => emit("view", row.original),
    },
    {
      label: "Editar",
      icon: "i-lucide-pencil",
      to: `/produtos/${row.original.id}/editar`,
    },
    { type: "separator" as const },
    {
      label: "Excluir",
      icon: "i-lucide-trash",
      color: "error" as const,
      onSelect: () => emit("delete", row.original.id),
    },
  ];
}

const table = useTemplateRef("table");

const columns = [
  { accessorKey: "id", header: "ID" },
  { accessorKey: "nome", header: "Nome" },
  {
    accessorKey: "tipo",
    header: "Tipo",
  },
  {
    accessorKey: "valorFornecedor",
    header: "Custo",
  },
  {
    accessorKey: "quantidadeEstoque",
    header: "Estoque",
  },
  {
    id: "actions",
    header: "Ações",
  },
];
</script>

<template>
  <div class="space-y-4">
    <div class="flex items-center gap-2">
      <UInput
        :model-value="
          table?.tableApi?.getColumn('nome')?.getFilterValue() as string
        "
        class="max-w-sm w-full"
        placeholder="Buscar produtos..."
        icon="i-lucide-search"
        @update:model-value="
          table?.tableApi?.getColumn('nome')?.setFilterValue($event)
        "
      />
    </div>

    <UTable
      ref="table"
      :data="produtos"
      :columns="columns"
      :loading="loading"
    >
      <template #empty>
        <UEmpty icon="i-lucide-package" title="Nenhum produto encontrado." />
      </template>
      <template #tipo-cell="{ row }">
        <UBadge
          :color="TIPO_PRODUTO_COLORS[row.original.tipo]"
          variant="subtle"
        >
          {{ TIPO_PRODUTO_LABELS[row.original.tipo] }}
        </UBadge>
      </template>

      <template #valorFornecedor-cell="{ row }">
        {{ formatCurrency(row.original.valorFornecedor) }}
      </template>

      <template #quantidadeEstoque-cell="{ row }">
        <span
          class="font-medium"
          :class="
            row.original.quantidadeEstoque < 5
              ? 'text-red-500'
              : 'text-green-600 dark:text-green-400'
          "
        >
          {{ formatNumber(row.original.quantidadeEstoque) }}
        </span>
      </template>

      <template #actions-cell="{ row }">
        <div class="flex justify-end">
          <UDropdownMenu :items="getRowItems(row)">
            <UButton
              color="neutral"
              variant="ghost"
              icon="i-lucide-more-vertical"
              aria-label="Opções"
            />
          </UDropdownMenu>
        </div>
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
