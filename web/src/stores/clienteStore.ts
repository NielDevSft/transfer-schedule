import Usuario, { Cliente } from "@/models/cliente";
import { ClienteService } from "@/services/ClienteService";
import { defineStore } from "pinia";
const clienteService = new ClienteService();

export const useClienteStore = defineStore("cliente", {
  state() {
    const clienteCorrente = JSON.parse(
      sessionStorage.getItem("clienteLogged") || "null",
    ) as Cliente;
    return {
      clienteStore: clienteCorrente || initCliente,
    };
  },
  actions: {
    async getClienteByUsuario(id: number) {
      try {
        const response = await clienteService.getAllByUser(id);
        this.clienteStore = response.data;
        sessionStorage.setItem("clienteLogged", JSON.stringify(response.data));
      } catch (error) {
        console.error("Erro ao buscar dados:", error);
      }
    },
    setCliente(cliente: Cliente) {
      this.clienteStore = cliente;
    },
  },
  getters: {
    clienteLogado(): Cliente {
      return this.clienteStore;
    },
  },
});

const usuario = new Usuario(0, "", "", true);
const initCliente = new Cliente("", "", new Date(""), null, usuario, "", 0);
