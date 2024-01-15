import { Transacao } from "@/models/transacao";
import { defineStore } from "pinia";

import { TransacaoService } from "@/services/TransacaoService";
import { ContaService } from "@/services/ContaService";

const transacaoService = new TransacaoService();
const contaService = new ContaService();

export const useTransacaoStore = defineStore("transacao", {
  state() {
    return {
      transacaoStore: [
        {
          transacao: {} as Transacao,
          selected: true,
        },
      ],
    };
  },
  actions: {
    async createTransacao(
      transacao: Transacao,
      codContaOrigem: number,
      codContaDestino: number,
    ) {
      try {
        const conOrigem = (await contaService.getByCodigo(codContaOrigem)).data;
        const conDestino = (await contaService.getByCodigo(codContaDestino))
          .data;
        transacao.contaOrigemUuid = conOrigem.uuid;
        transacao.contaDestinoUuid = conDestino.uuid;
        const response = await transacaoService.create(transacao);
        this.transacaoStore = [{ transacao: response.data, selected: true }];
      } catch (error) {
        console.error("Erro ao buscar dados:", error);
      }
    },

    async getAll(cliUuid: string) {
      try {
        const response = await transacaoService.getAll();
        this.transacaoStore = response.data.map((tra) => {
          return { transacao: tra, selected: false };
        });
      } catch (error) {
        console.error("Erro ao buscar dados:", error);
      }
    },
  },
  getters: {
    transacaoCorrente(): Transacao {
      const con = this.transacaoStore.find((t) => t.selected);
      return con ? con.transacao : initTransacao;
    },
    transacaoList(): typeof this.transacaoStore {
      return this.transacaoStore;
    },
  },
});
const initTransacao = new Transacao("", 0, 0, 0, new Date(), "", "", "");
