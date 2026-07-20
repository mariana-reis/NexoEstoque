<script setup lang="ts">
import { z } from "zod";
import { TIPO_PRODUTO_OPTIONS } from "~/utils/constants";
import type { ProdutoForm } from "~/types/produto";

const props = defineProps<{
  initialData?: Partial<ProdutoForm>;
  isLoading?: boolean;
}>();

const emit = defineEmits<{
  (e: "submit", data: ProdutoForm): void;
  (e: "cancel"): void;
}>();

const schema = z.object({
  nome: z
    .string()
    .min(1, "Nome é obrigatório")
    .max(100, "Máximo 100 caracteres"),
  descricao: z.string().max(255, "Máximo 255 caracteres").optional(),
  tipo: z.enum(["ELETRONICO", "ELETRODOMESTICO", "MOVEL"], {
    message: "Selecione um tipo",
  }),
  valorFornecedor: z
    .number({
      error: "Valor obrigatório"
    })
    .min(0.01, "Deve ser maior que zero"),

  quantidadeEstoque: z
    .number({
      error: "Quantidade obrigatória"
    })
    .min(0, "Não pode ser negativo"),
});

const defaultData = {
  nome: "",
  descricao: "",
  tipo: undefined,
  valorFornecedor: undefined,
  quantidadeEstoque: undefined,
};

const state = reactive({
  ...defaultData,
  ...props.initialData,
});

async function onSubmit(event: any) {
  emit("submit", event.data as ProdutoForm);
}
</script>

<template>
  <UForm :schema="schema" :state="state" class="space-y-4" @submit="onSubmit">
    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
      <UFormField label="Nome do Produto" name="nome" required>
        <UInput
          v-model="state.nome"
          placeholder="Ex: Notebook Dell"
          class="w-full"
        />
      </UFormField>

      <UFormField label="Tipo" name="tipo" required>
        <USelect
          v-model="state.tipo"
          :items="TIPO_PRODUTO_OPTIONS"
          placeholder="Selecione..."
          class="w-full"
        />
      </UFormField>
    </div>

    <UFormField label="Descrição" name="descricao">
      <UTextarea
        v-model="state.descricao"
        placeholder="Detalhes opcionais do produto"
        :rows="3"
        class="w-full"
      />
    </UFormField>

    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
      <UFormField
        label="Valor de Custo (Fornecedor)"
        name="valorFornecedor"
        required
      >
        <UInput
          v-model.number="state.valorFornecedor"
          type="number"
          step="0.01"
          min="0"
          class="w-full"
        />
      </UFormField>

      <UFormField label="Estoque Inicial" name="quantidadeEstoque" required>
        <UInput
          v-model.number="state.quantidadeEstoque"
          type="number"
          min="0"
          class="w-full"
        />
      </UFormField>
    </div>

    <div
      class="flex justify-end gap-3 pt-4 border-t border-gray-200 dark:border-gray-800"
    >
      <UButton
        label="Cancelar"
        type="button"
        color="neutral"
        variant="ghost"
        @click="emit('cancel')"
      />
      <UButton
        label="Salvar Produto"
        type="submit"
        color="primary"
        :loading="isLoading"
      />
    </div>
  </UForm>
</template>
