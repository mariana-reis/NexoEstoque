import type { FetchOptions } from "ofetch";
import type { ApiError } from "~/types/api";

export function useApi() {
  const toast = useToast();
  const config = useRuntimeConfig();

  const api = $fetch.create({
    baseURL: config.public.apiBase,
  });

  function handleApiError(error: any): never {
    const apiError: ApiError = error.response?._data || {
      message: error.message || "Erro de conexão com o servidor",
      status: error.response?.status || 500,
    };

    let errorMessage = apiError.message;

    if (apiError.status === 400 && apiError.details) {
      errorMessage = `Erro de validação: ${Object.values(apiError.details).join(", ")}`;
    }

    toast.add({
      title: "Erro na operação",
      description: errorMessage,
      color: "error",
      icon: "i-lucide-alert-circle",
    });

    throw apiError;
  }

  async function fetchApi<T>(
    request: string,
    opts?: Parameters<typeof api>[1],
  ): Promise<T> {
    try {
      return await api<T>(request, opts);
    } catch (error: any) {
      handleApiError(error);
    }
  }

  return {
    fetchApi,
  };
}
