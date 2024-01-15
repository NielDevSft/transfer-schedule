import { Cliente } from "@/models/cliente";
import { Conta } from "@/models/conta";
import Usuario from "@/models/usuario";
import { ContaService } from "@/services/ContaService";
import { defineStore } from "pinia";

const contaService = new ContaService();

export const useContaStore = defineStore("conta", {
  state() {
    const contaCorrente = JSON.parse(
      sessionStorage.getItem("consta") || "null",
    ) as Conta;
    return {
      contaStore: [
        {
          conta: contaCorrente || initConta,
          selected: true,
        },
      ],
    };
  },
  actions: {
    async createPrimeiraConta(cliente: Cliente) {
      try {
        const response = await contaService.createPrimeiraConta(cliente);
        this.contaStore = [{ conta: response.data, selected: true }];
        sessionStorage.setItem("contaCorrente", JSON.stringify(response.data));
      } catch (error) {
        console.error("Erro ao buscar dados:", error);
      }
    },
    async getContaByClienteUuid(uuid: string) {
      try {
        const response = await contaService.getAllByCliente(uuid);
        this.contaStore = response.data.map((con) => {
          return { conta: con, selected: false };
        });
      } catch (error) {
        console.error("Erro ao buscar dados:", error);
      }
    },
    async setContaCorrete(uuid: string) {
      try {
        if (this.contaStore.length) {
          this.contaStore.forEach((conStore) => {
            conStore.selected = false;
          });
          const contaInMemory = this.contaStore.find(
            (con) => con.conta.uuid === uuid,
          );
          if (contaInMemory) contaInMemory.selected = true;
        } else {
          await contaService.getAllByCliente(uuid);
          this.setContaCorrete(uuid);
        }
      } catch (error) {
        console.error("Erro ao buscar dados:", error);
      }
    },
  },
  getters: {
    contaCorrente(): Conta {
      const con = this.contaStore.find((c) => c.selected);
      return con ? con.conta : initConta;
    },
    contaList(): Conta[] {
      return this.contaStore.map((cs) => cs.conta);
    },
  },
});

const usuario = new Usuario(0, "", "", true);
const cliente = new Cliente("", "", new Date(""), usuario, "", 0);
const initConta = new Conta(
  "",
  false,
  new Date(""),
  new Date(""),
  null,
  0,
  cliente,
);
