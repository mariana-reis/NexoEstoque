import type { FetchOptions } from "ofetch";
import type { ApiError } from "~/types/api";

export function useApi() {
  const toast = useToast();

  function handleApiError(error: any): never {
    const apiError: ApiError = error.response?._data || {
      message: error.message || "Erro de conexão com o servidor",
      status: error.response?.status || 500,
    };

    let errorMessage = apiError.message;

    if (apiError.status === 400 && apiError.details) {
      const details = Object.values(apiError.details).join(", ");
      errorMessage = `Erro de validação: ${details}`;
    }

    toast.add({
      title: "Erro na operação",
      description: errorMessage,
      color: "error",
      icon: "i-lucide-alert-circle",
    });

    throw apiError;
  }

  const config = useRuntimeConfig();

  async function fetchApi<T>(
    request: string,
    opts?: FetchOptions<"json">,
  ): Promise<T> {
    try {
      const path = request.startsWith("/api")
        ? request.replace("/api", "")
        : request;

      return await $fetch<T>(path, {
        ...opts,
        baseURL: config.public.apiBaseUrl as string,
      } as any);
    } catch (error: any) {
      handleApiError(error);
    }
  }

  return {
    fetchApi,
  };
}
