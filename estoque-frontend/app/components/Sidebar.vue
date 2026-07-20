<script setup lang="ts">
import type { NavigationMenuItem } from "@nuxt/ui";

const open = useState("sidebar-open", () => true);

const items = computed<NavigationMenuItem[]>(() => [
  {
    label: "Dashboard",
    icon: "i-lucide-layout-dashboard",
    to: "/",
  },
  {
    label: "Produtos",
    icon: "i-lucide-package",
    to: "/produtos",
  },
  {
    label: "Movimentações",
    icon: "i-lucide-arrow-left-right",
    to: "/movimentos",
  },
  {
    label: "Consultas",
    icon: "i-lucide-chart-column",
    defaultOpen: true,
    children: [
      {
        label: "Por Tipo",
        icon: "i-lucide-pie-chart",
        to: "/consultas/por-tipo",
      },
      {
        label: "Análise de Lucro",
        icon: "i-lucide-trending-up",
        to: "/consultas/lucro",
      },
    ],
  },
]);
</script>

<template>
  <USidebar
    v-model:open="open"
    collapsible="icon"
    rail
    :ui="{
      container: 'h-full',
      inner: 'divide-transparent',
      body: 'py-2',
    }"
  >
    <template #header>
      <div
        class="flex items-center gap-2 px-1 text-primary font-bold text-xl overflow-hidden whitespace-nowrap"
      >
        <UIcon name="i-lucide-box" class="w-6 h-6 shrink-0" />
        <span v-if="open" class="truncate">NexoEstoque</span>
      </div>
    </template>

    <template #default>
      <UNavigationMenu
        :items="items"
        orientation="vertical"
        :ui="{ link: 'p-1.5 overflow-hidden' }"
      />
    </template>

    <template #footer>
      <div
        class="flex items-center gap-2 px-1 text-xs text-gray-400 overflow-hidden"
      >
        <UIcon name="i-lucide-info" class="w-4 h-4 shrink-0" />
        <span v-if="open">Copyright © - {{ new Date().getFullYear() }}</span>
      </div>
    </template>
  </USidebar>
</template>
