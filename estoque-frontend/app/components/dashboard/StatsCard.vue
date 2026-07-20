<script setup lang="ts">
defineProps<{
  title: string;
  value: string | number;
  icon: string;
  color?: "primary" | "success" | "warning" | "error" | "info" | "neutral";
  description?: string;
}>();
</script>

<template>
  <UCard
    class="relative overflow-hidden hover:shadow-md transition-shadow"
    :ui="{ body: 'p-4 sm:p-5' }"
  >
    <div class="flex flex-col gap-3">
      <div class="flex items-start justify-between gap-3">
        <h3
          class="text-sm font-medium text-gray-500 dark:text-gray-400 leading-snug"
        >
          {{ title }}
        </h3>

        <div
          class="p-1.5 rounded-xl shrink-0"
          :class="{
            'text-primary-500 dark:text-primary-400 bg-primary-50 dark:bg-primary-950/50':
              color === 'primary',
            'text-green-500 dark:text-green-400 bg-green-50 dark:bg-green-950/50':
              color === 'success',
            'text-amber-500 dark:text-amber-400 bg-amber-50 dark:bg-amber-950/50':
              color === 'warning',
            'text-red-500 dark:text-red-400 bg-red-50 dark:bg-red-950/50':
              color === 'error',
            'text-blue-500 dark:text-blue-400 bg-blue-50 dark:bg-blue-950/50':
              color === 'info',
            'text-gray-500 dark:text-gray-400 bg-gray-50 dark:bg-gray-800':
              !color || color === 'neutral',
          }"
        >
          <UIcon :name="icon" class="w-5 h-5" />
        </div>
      </div>

      <p
        class="text-2xl sm:text-2xl font-bold text-gray-900 dark:text-white tabular-nums leading-tight wrap-break-word"
        :title="typeof value === 'string' ? value : undefined"
      >
        {{ value }}
      </p>

      <p
        v-if="description"
        class="text-sm text-gray-500 dark:text-gray-400 leading-snug"
      >
        {{ description }}
      </p>
    </div>
  </UCard>
</template>
