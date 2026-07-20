export default defineNuxtConfig({
  modules: ['@nuxt/ui', '@pinia/nuxt'],
  css: ['~/assets/css/main.css',],
  compatibilityDate: '2025-07-15',
  devtools: { enabled: true },
  runtimeConfig: {
    public: {
      apiBaseUrl: process.env.NUXT_PUBLIC_API_BASE || 'http://localhost:8080'
    }
  },
  vite: {
    optimizeDeps: {
      include: [
        'date-fns',
        'date-fns/locale',
        'zod'
      ]
    }
  },
  app: {
    head: {
      title: 'NexoEstoque - Gestão de Estoque',
      meta: [
        { name: 'description', content: 'NexoEstoque - Sistema corporativo de gestão de estoque, movimentações e análise de lucratividade' }
      ],
      htmlAttrs: {
        lang: 'pt-BR'
      }
    }
  }
})