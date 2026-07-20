<script setup lang="ts">
import { z } from "zod";
import { useProdutoStore } from "~/stores/produto";
import { type TipoMovimentacao } from "~/types/movimento";

const props = defineProps<{
  isLoading?: boolean;
}>();

const emit = defineEmits<{
  (e: "submit", data: any): void;
  (e: "cancel"): void;
}>();

const produtoStore = useProdutoStore();

onMounted(async () => {
  if (produtoStore.produtos.length === 0) {
    await produtoStore.carregarLista({ page: 0, size: 1000 });
  }
});

const produtoOptions = computed(() => {
  return produtoStore.produtos.map((p) => ({
    label: `${p.nome} (Estoque: ${p.quantidadeEstoque})`,
    value: p.id,
    quantidadeEstoque: p.quantidadeEstoque,
  }));
});

const tipoOptions = [
  { label: "Entrada (+)", value: "ENTRADA" },
  { label: "Saída (-)", value: "SAIDA" },
];

const schema = z.object({
  produtoId: z.number({ message: "Selecione um produto" }),
  tipo: z.enum(["ENTRADA", "SAIDA"], {
    message: "Selecione o tipo de movimentação",
  }),
  quantidade: z
    .number({ message: "Quantidade obrigatória" })
    .min(1, "Mínimo 1 unidade"),
  valorVenda: z
    .number({ message: "Valor obrigatório" })
    .min(0, "Não pode ser negativo"),
  dataMovimentacao: z.string({ message: "Data obrigatória" }),
});

const state = reactive({
  produtoId: undefined as number | undefined,
  tipo: undefined as TipoMovimentacao | undefined,
  quantidade: 1,
  valorVenda: 0,
  dataMovimentacao: new Date().toISOString().split("T")[0],
});

const validate = (state: any): { path: string; message: string }[] => {
  const errors = [];
  if (state.tipo === "SAIDA" && state.produtoId && state.quantidade) {
    const produto = produtoStore.produtos.find((p) => p.id === state.produtoId);
    if (produto && state.quantidade > produto.quantidadeEstoque) {
      errors.push({
        path: "quantidade",
        message: `Saldo insuficiente. Disponível: ${produto.quantidadeEstoque}`,
      });
    }
  }
  return errors;
};

async function onSubmit() {
  const payload = {
    produto: { id: state.produtoId },
    tipo: state.tipo,
    quantidade: state.quantidade,
    valorVenda: state.valorVenda,
    dataMovimentacao: state.dataMovimentacao,
  };
  emit("submit", payload);
}
</script>

<template>
  <UForm
    :schema="schema"
    :state="state"
    :validate="validate"
    class="space-y-5"
    @submit="onSubmit"
  >
    <UFormField label="Produto" name="produtoId" required>
      <USelect
        v-model="state.produtoId"
        :items="produtoOptions"
        placeholder="Selecione um produto..."
        class="w-full"
        size="lg"
      />
    </UFormField>

    <div class="grid grid-cols-1 sm:grid-cols-2 gap-5">
      <UFormField label="Tipo de Movimentação" name="tipo" required>
        <USelect
          v-model="state.tipo"
          :items="tipoOptions"
          placeholder="Selecione..."
          class="w-full"
          size="lg"
        />
      </UFormField>

      <UFormField label="Data da Movimentação" name="dataMovimentacao" required>
        <UInput
          v-model="state.dataMovimentacao"
          type="date"
          class="w-full"
          size="lg"
        />
      </UFormField>
    </div>

    <div class="grid grid-cols-1 sm:grid-cols-2 gap-5">
      <UFormField label="Quantidade" name="quantidade" required>
        <UInput
          v-model.number="state.quantidade"
          type="number"
          min="1"
          class="w-full"
          size="lg"
        />
      </UFormField>

      <UFormField label="Valor Unitário de Venda" name="valorVenda" required>
        <UInput
          v-model.number="state.valorVenda"
          type="number"
          step="0.01"
          min="0"
          class="w-full"
          :disabled="state.tipo === 'ENTRADA'"
          size="lg"
        />
        <template #help>
          <span class="text-xs text-gray-500">Obrigatório para Saídas.</span>
        </template>
      </UFormField>
    </div>

    <div class="flex flex-col-reverse sm:flex-row justify-end gap-3 pt-5 mt-2">
      <UButton
        label="Cancelar"
        type="button"
        color="neutral"
        variant="ghost"
        @click="emit('cancel')"
        size="lg"
        class="justify-center"
      />
      <UButton
        label="Confirmar Movimentação"
        type="submit"
        color="primary"
        :loading="isLoading"
        size="lg"
        class="justify-center"
      />
    </div>
  </UForm>
</template>
