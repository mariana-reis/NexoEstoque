<template>
  <div class="flex min-h-screen bg-gray-50 dark:bg-gray-950 font-sans">
    <Sidebar />

    <div class="flex-1 flex flex-col overflow-hidden">
      <Header />

      <main class="flex-1 overflow-y-auto p-4 md:p-6">
        <div class="mx-auto max-w-7xl">
          <slot />
        </div>
      </main>
    </div>
  </div>
</template>
