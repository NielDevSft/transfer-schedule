export const useUtils = () => {
  const convertStringToDate = (dateString: string): Date => {
    const [year, month, day] = dateString.split("-").map(Number);
    const adjustedMonth = month - 1;
    const dateObject = new Date(year, adjustedMonth, day);

    return dateObject;
  };
  const formatarNumeroConta = (numero: string) => {
    let numeroTexto = numero.toString();
    return numeroTexto.padStart(6, "0");
  };
  return {
    convertStringToDate,
    formatarNumeroConta,
  };
};
