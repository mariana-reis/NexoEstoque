<script setup lang="ts">
import { TIPO_PRODUTO_OPTIONS } from "~/utils/constants";
import type { TipoProduto } from "~/types/produto";
import type { SelectItem } from "@nuxt/ui";

const store = useProdutoStore();
const toast = useToast();

const page = ref(0);
const pageSize = ref(10);
type FiltroTipo = TipoProduto | "ALL";

const filtroTipo = ref<FiltroTipo>("ALL");

const tipoOptions: SelectItem[] = [
  {
    label: "Todos os tipos",
    value: "ALL",
  },
  ...TIPO_PRODUTO_OPTIONS,
];

watchEffect(async () => {
  await store.carregarLista(
    {
      page: page.value,
      size: pageSize.value,
    },
    filtroTipo.value === "ALL" ? undefined : filtroTipo.value,
  );
});

const modalExclusao = reactive({
  isOpen: false,
  produtoId: 0,
});

function confirmarExclusao(id: number) {
  modalExclusao.produtoId = id;
  modalExclusao.isOpen = true;
}

async function deletarProduto() {
  try {
    await store.remover(modalExclusao.produtoId);
    toast.add({
      title: "Produto excluído",
      description: "O produto foi removido com sucesso.",
      color: "success",
      icon: "i-lucide-check-circle",
    });
  } catch (error) {
    console.error(error);
  } finally {
    modalExclusao.isOpen = false;
  }
}

const modalDetalhes = reactive({
  isOpen: false,
  produto: null as any,
});

function abrirModalDetalhes(produto: any) {
  modalDetalhes.produto = produto;
  modalDetalhes.isOpen = true;
}
</script>

<template>
  <div class="space-y-6">
    <div
      class="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4"
    >
      <div class="flex items-center gap-4">
        <USelect v-model="filtroTipo" :items="tipoOptions" class="w-48" />
      </div>

      <UButton
        label="Novo Produto"
        to="/produtos/novo"
        icon="i-lucide-plus"
        color="primary"
      />
    </div>

    <UCard>
      <ProdutoTable
        :produtos="store.produtos"
        :loading="store.isCarregando"
        :total="store.totalElements"
        :page="page"
        :page-size="pageSize"
        @update:page="page = $event"
        @delete="confirmarExclusao"
        @view="abrirModalDetalhes"
      />
    </UCard>

    <UModal v-model:open="modalDetalhes.isOpen" title="Detalhes do Produto">
      <template #body>
        <div v-if="modalDetalhes.produto" class="space-y-4">
          <div>
            <span class="font-semibold block text-sm text-gray-500">Nome:</span>
            <span class="text-gray-900 dark:text-white">{{
              modalDetalhes.produto.nome
            }}</span>
          </div>
          <div>
            <span class="font-semibold block text-sm text-gray-500"
              >Descrição:</span
            >
            <span class="text-gray-900 dark:text-white">{{
              modalDetalhes.produto.descricao || "Nenhuma descrição fornecida."
            }}</span>
          </div>
          <div class="grid grid-cols-2 gap-4">
            <div>
              <span class="font-semibold block text-sm text-gray-500"
                >Tipo:</span
              >
              <span class="text-gray-900 dark:text-white">{{
                modalDetalhes.produto.tipo
              }}</span>
            </div>
            <div>
              <span class="font-semibold block text-sm text-gray-500"
                >Estoque Atual:</span
              >
              <span class="text-gray-900 dark:text-white">{{
                modalDetalhes.produto.quantidadeEstoque
              }}</span>
            </div>
            <div>
              <span class="font-semibold block text-sm text-gray-500"
                >Valor de Custo (Fornecedor):</span
              >
              <span class="text-gray-900 dark:text-white">{{
                new Intl.NumberFormat("pt-BR", {
                  style: "currency",
                  currency: "BRL",
                }).format(modalDetalhes.produto.valorFornecedor)
              }}</span>
            </div>
          </div>
        </div>
      </template>
      <template #footer>
        <div class="flex justify-end w-full">
          <UButton
            label="Fechar"
            color="neutral"
            @click="
              () => {
                modalDetalhes.isOpen = false;
              }
            "
          />
        </div>
      </template>
    </UModal>

    <UModal v-model:open="modalExclusao.isOpen" title="Excluir Produto">
      <template #body>
        <div class="flex items-center gap-4">
          <div
            class="p-3 bg-red-100 dark:bg-red-900/30 text-red-600 rounded-full shrink-0"
          >
            <UIcon name="i-lucide-alert-triangle" class="w-6 h-6" />
          </div>
          <div>
            <h3 class="text-lg font-medium text-gray-900 dark:text-white">
              Atenção
            </h3>
            <p class="text-gray-500 dark:text-gray-400 mt-1">
              Tem certeza que deseja excluir este produto? Esta ação não pode
              ser desfeita.
            </p>
          </div>
        </div>
      </template>

      <template #footer>
        <div class="flex justify-end gap-3 w-full">
          <UButton
            label="Cancelar"
            color="neutral"
            variant="outline"
            @click="
              () => {
                modalExclusao.isOpen = false;
              }
            "
          />
          <UButton label="Sim, excluir" color="error" @click="deletarProduto" />
        </div>
      </template>
    </UModal>
  </div>
</template>
