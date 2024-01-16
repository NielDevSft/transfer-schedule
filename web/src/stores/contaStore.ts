import { Cliente } from "@/models/cliente";
import { Conta } from "@/models/conta";
import Usuario from "@/models/usuario";
import { useUtils } from "@/models/utils";
import { ContaService } from "@/services/ContaService";
import { defineStore } from "pinia";

import { useToast } from "vue-toastification";

const contaService = new ContaService();
const toast = useToast();
const utils = useUtils();

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
        this.contaStore = [
          {
            conta: {
              ...response.data,
              contaLabel: utils.formatarNumeroConta(
                response.data.codConta.toString(),
              ),
            },
            selected: true,
          },
        ];
        sessionStorage.setItem("contaCorrente", JSON.stringify(response.data));
        toast.success(
          `Cliente criado com sucesso e conta padrão de número ${utils.formatarNumeroConta(
            response.data.codConta.toString(),
          )}`,
        );
      } catch (error) {
        toast.error("Falha ao criar cliente");
        console.error(error);
      }
    },
    async createConta(cliente: Cliente) {
      try {
        const response = await contaService.createConta(cliente);
        this.contaStore = [
          ...this.contaStore,
          {
            conta: {
              ...response.data,
              contaLabel: utils.formatarNumeroConta(
                response.data.codConta.toString(),
              ),
            },
            selected: false,
          },
        ];
        toast.success(
          `Conta ${utils.formatarNumeroConta(
            response.data.codConta.toString(),
          )} criada com sucesso`,
        );
      } catch (error) {
        toast.error("Falha ao criar conta");
      }
    },
    async getContaByClienteUuid(uuid: string) {
      try {
        if (!this.contaStore.length || !this.contaStore[0].conta.uuid) {
          const response = await contaService.getAllByCliente(uuid);
          this.contaStore = response.data.map((con) => {
            return {
              conta: {
                ...con,
                contaLabel: utils.formatarNumeroConta(con.codConta.toString()),
              },
              selected: false,
            };
          });
        }
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
    contaList(): typeof this.contaStore {
      return this.contaStore;
    },
  },
});

const usuario = new Usuario(0, "", "", true);
const cliente = new Cliente("", "", new Date(""), null, usuario, "", 0);
const initConta = new Conta(
  "",
  false,
  new Date(""),
  new Date(""),
  null,
  0,
  cliente,
);
