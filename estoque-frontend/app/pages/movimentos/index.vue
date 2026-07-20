<script setup lang="ts">
import { useMovimentos } from "~/composables/useMovimentos";
import { useMovimentoStore } from "~/stores/movimento";
import { useProdutoStore } from "~/stores/produto";
import type { MovimentoForm } from "~/types/movimento";

const store = useMovimentoStore();
const toast = useToast();
const { registrarMovimentacao } = useMovimentos();
const produtoStore = useProdutoStore();

const page = ref(0);
const pageSize = ref(10);

watchEffect(async () => {
  await store.carregarLista({
    page: page.value,
    size: pageSize.value,
    sort: "dataMovimentacao,desc",
  });
});

const modalRegistro = reactive({
  isOpen: false,
  isLoading: false,
});

function abrirModalRegistro() {
  modalRegistro.isOpen = true;
}

async function registrar(data: MovimentoForm) {
  modalRegistro.isLoading = true;
  try {
    await registrarMovimentacao(data);
    toast.add({
      title: "Movimentação registrada",
      description: "O estoque foi atualizado com sucesso.",
      color: "success",
      icon: "i-lucide-check-circle",
    });

    await store.carregarLista({
      page: page.value,
      size: pageSize.value,
      sort: "dataMovimentacao,desc",
    });
    await produtoStore.carregarLista({ page: 0, size: 100 });

    modalRegistro.isOpen = false;
  } catch (error) {
    console.error(error);
  } finally {
    modalRegistro.isLoading = false;
  }
}
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
          Histórico de Movimentações
        </h2>
        <p class="text-sm text-gray-500 dark:text-gray-400">
          Acompanhe todas as entradas e saídas de estoque.
        </p>
      </div>

      <UButton
        label="Nova Movimentação"
        icon="i-lucide-arrow-left-right"
        color="primary"
        @click="abrirModalRegistro"
      />
    </div>

    <UCard>
      <MovimentoTable
        :movimentos="store.movimentos"
        :loading="store.isCarregando"
        :total="store.totalElements"
        :page="page"
        :page-size="pageSize"
        @update:page="page = $event"
      />
    </UCard>

    <UModal v-model:open="modalRegistro.isOpen" title="Registrar Movimentação">
      <template #body>
        <div class="flex items-center gap-2 text-primary font-semibold mb-6">
          <UIcon name="i-lucide-arrow-left-right" class="w-5 h-5" />
          <h2>Nova Movimentação de Estoque</h2>
        </div>

        <MovimentoForm
          :is-loading="modalRegistro.isLoading"
          @submit="registrar"
          @cancel="modalRegistro.isOpen = false"
        />
      </template>
    </UModal>
  </div>
</template>
