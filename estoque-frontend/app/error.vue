<script setup lang="ts">
import type { NuxtError } from "#app";

const props = defineProps({
  error: Object as () => NuxtError,
});

const handleError = () => clearError({ redirect: "/" });
</script>

<template>
  <div
    class="min-h-screen flex items-center justify-center bg-gray-50 dark:bg-gray-950 px-4 py-12"
  >
    <UCard class="max-w-md w-full text-center">
      <div class="mb-4">
        <UIcon
          :name="
            error?.status === 404
              ? 'i-lucide-file-question'
              : 'i-lucide-alert-triangle'
          "
          class="w-16 h-16 mx-auto"
          :class="error?.status === 404 ? 'text-blue-500' : 'text-red-500'"
        />
      </div>

      <h1 class="text-3xl font-bold text-gray-900 dark:text-white mb-2">
        {{
          error?.status === 404 ? "Página não encontrada" : "Ocorreu um erro"
        }}
      </h1>

      <p class="text-gray-500 dark:text-gray-400 mb-6">
        {{
          error?.status === 404
            ? "A URL que você tentou acessar não existe ou foi movida."
            : error?.message || "Erro inesperado no servidor."
        }}
      </p>

      <UButton color="primary" size="lg" @click="handleError">
        Voltar para o Início
      </UButton>
    </UCard>
  </div>
</template>
