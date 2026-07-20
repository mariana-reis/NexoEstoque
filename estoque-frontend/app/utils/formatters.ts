import { format, isValid, parseISO } from "date-fns";
import { ptBR } from "date-fns/locale";

function isNilOrInvalidNumber(
  value: number | string | undefined | null,
): boolean {
  return value === null || value === undefined || isNaN(Number(value));
}

export function formatCurrency(
  value: number | string | undefined | null,
): string {
  if (isNilOrInvalidNumber(value)) {
    return new Intl.NumberFormat("pt-BR", {
      style: "currency",
      currency: "BRL",
    }).format(0);
  }
  return new Intl.NumberFormat("pt-BR", {
    style: "currency",
    currency: "BRL",
  }).format(Number(value));
}

export function formatDate(dateString: string | undefined | null): string {
  if (!dateString) return "-";

  try {
    const date = parseISO(dateString);
    if (!isValid(date)) return "-";

    return format(date, "dd/MM/yyyy", { locale: ptBR });
  } catch (e) {
    return dateString || "-";
  }
}

export function formatNumber(
  value: number | string | undefined | null,
): string {
  if (isNilOrInvalidNumber(value)) return "0";
  return new Intl.NumberFormat("pt-BR").format(Number(value));
}
