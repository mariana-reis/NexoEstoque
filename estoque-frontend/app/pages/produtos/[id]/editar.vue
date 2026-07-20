<script setup lang="ts">
import { useProdutos } from "~/composables/useProdutos";
import type { Produto, ProdutoForm } from "~/types/produto";

const route = useRoute();
const router = useRouter();
const toast = useToast();
const { buscarProduto, atualizarProduto } = useProdutos();

const produtoId = Number(route.params.id);
const produto = ref<Produto | null>(null);
const isLoading = ref(true);
const isSaving = ref(false);

onMounted(async () => {
  try {
    produto.value = await buscarProduto(produtoId);
  } catch (error) {
    router.replace("/produtos");
  } finally {
    isLoading.value = false;
  }
});

async function handleSubmit(data: ProdutoForm) {
  isSaving.value = true;
  try {
    await atualizarProduto(produtoId, data);
    toast.add({
      title: "Sucesso!",
      description: "Produto atualizado com sucesso.",
      color: "success",
      icon: "i-lucide-check-circle",
    });
    router.push("/produtos");
  } catch (error) {
    console.error(error);
  } finally {
    isSaving.value = false;
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
          <UIcon name="i-lucide-edit" class="w-5 h-5" />
          <h2>Editar Produto</h2>
        </div>
      </template>

      <div v-if="isLoading" class="flex justify-center py-8">
        <UIcon
          name="i-lucide-loader-2"
          class="w-8 h-8 animate-spin text-primary"
        />
      </div>

      <ProdutoForm
        v-else-if="produto"
        :initial-data="produto"
        :is-loading="isSaving"
        @submit="handleSubmit"
        @cancel="handleCancel"
      />
    </UCard>
  </div>
</template>
