import Usuario, { Cliente } from "@/models/cliente";
import { ClienteService } from "@/services/ClienteService";
import { defineStore } from "pinia";
const clienteService = new ClienteService();

export const useClienteStore = defineStore("cliente", {
  state() {
    return {
      clienteLogado: new Cliente(
        "",
        "",
        "",
        0,
        new Usuario(0, "", ""),
        new Date(),
      ),
    };
  },
  actions: {
    async getClienteByUsuario(id: number) {
      try {
        const response = await clienteService.getAllByUser(id);
        this.clienteLogado = response.data;
      } catch (error) {
        console.error("Erro ao buscar dados:", error);
      }
    },
  },
  getters: {
    clienteLogado(): Cliente {
      return this.clienteLogado;
    },
  },
});
