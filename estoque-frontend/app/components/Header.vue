<script setup lang="ts">
const route = useRoute();
const open = useState("sidebar-open", () => true);

const breadcrumbs = computed(() => {
  const path = route.path;
  const crumbs: { label: string; to?: string }[] = [
    { label: "NexoEstoque", to: "/" },
  ];

  if (path.startsWith("/produtos/novo"))
    crumbs.push({ label: "Produtos", to: "/produtos" }, { label: "Novo" });
  else if (path.includes("/editar"))
    crumbs.push({ label: "Produtos", to: "/produtos" }, { label: "Editar" });
  else if (path.startsWith("/produtos")) crumbs.push({ label: "Produtos" });
  else if (path.startsWith("/movimentos"))
    crumbs.push({ label: "Movimentações" });
  else if (path === "/consultas/por-tipo")
    crumbs.push(
      { label: "Consultas", to: "/consultas/por-tipo" },
      { label: "Por Tipo" },
    );
  else if (path === "/consultas/lucro")
    crumbs.push(
      { label: "Consultas", to: "/consultas/lucro" },
      { label: "Análise de Lucro" },
    );

  return crumbs;
});

</script>

<template>
  <UHeader
    :ui="{ root: 'border-b border-default bg-default/80 backdrop-blur' }"
  >
    <template #left>
      <UTooltip :text="open ? 'Recolher menu' : 'Expandir menu'">
        <UButton
          :icon="open ? 'i-lucide-panel-left-close' : 'i-lucide-panel-left'"
          color="neutral"
          variant="ghost"
          aria-label="Alternar barra lateral"
          @click="open = !open"
        />
      </UTooltip>

      <UBreadcrumb :items="breadcrumbs" class="ml-1" />
    </template>

    <template #right>
      <UColorModeButton />
    </template>
  </UHeader>
</template>
