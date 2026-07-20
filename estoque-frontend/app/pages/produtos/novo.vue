<script setup lang="ts">
import { useProdutos } from "~/composables/useProdutos";
import type { ProdutoForm } from "~/types/produto";

const router = useRouter();
const toast = useToast();
const { criarProduto } = useProdutos();

const isLoading = ref(false);

async function handleSubmit(data: ProdutoForm) {
  isLoading.value = true;
  try {
    await criarProduto(data);
    toast.add({
      title: "Sucesso!",
      description: "Produto cadastrado com sucesso.",
      color: "success",
      icon: "i-lucide-check-circle",
    });
    router.push("/produtos");
  } catch (error) {
    console.error(error);
  } finally {
    isLoading.value = false;
  }
}

function handleCancel() {
  router.back();
}
</script>

<template>
  <div class="max-w-3xl mx-auto space-y-6">
    <UCard>
      <template #header>
        <div class="flex items-center gap-2 text-primary font-semibold">
          <UIcon name="i-lucide-package-plus" class="w-5 h-5" />
          <h2>Cadastrar Novo Produto</h2>
        </div>
      </template>

      <ProdutoForm
        :is-loading="isLoading"
        @submit="handleSubmit"
        @cancel="handleCancel"
      />
    </UCard>
  </div>
</template>
