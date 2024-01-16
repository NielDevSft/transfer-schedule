<template>
  <v-card>
    <v-card-title class="d-flex justify-space-between align-center">
      Trasações agendadas
      <v-btn
        class="ml-2 text-end"
        left
        color="success"
        size="small"
        v-on:click="goFromTransacao()"
      >
        <v-icon icon="$plus" size="x-small"></v-icon>
        Agendar transação</v-btn
      >
    </v-card-title>
    <v-table theme="dark">
      <thead>
        <tr>
          <th class="text-left">#</th>
          <th class="text-left">Autor</th>
          <th class="text-left">Nun. Conta Origem</th>
          <th class="text-left">Nun. Conta Destido</th>
          <th class="text-left">Tipo de Operaçao</th>
          <th class="text-left">Data criação</th>
          <th class="text-left">Data agendada</th>
          <th class="text-left">Valor transação</th>
          <th class="text-left">Taxa prevista</th>
          <th class="text-left"></th>
        </tr>
      </thead>
      <tbody v-if="!!transacaoStore.transacaoList.length">
        <tr
          v-for="item in transacaoStore.transacaoList"
          :key="item.transacao.uuid"
        >
          <td>{{ item.transacao.codTransacao }}</td>
          <td>{{ item.transacao.clienteResponsavel?.desNomeCompleto }}</td>
          <td>
            {{ utils.formatarNumeroConta(item.transacao.codContaOrigem) }}
          </td>
          <td>
            {{ utils.formatarNumeroConta(item.transacao.codContaDestico) }}
          </td>
          <dh>{{ item.transacao.indTipoOperacao }}</dh>
          <td>
            {{ new Date(item.transacao.dtaCreateAt).toLocaleDateString() }}
          </td>
          <td>
            {{ new Date(item.transacao.dtaTransacao).toLocaleDateString() }}
          </td>
          <td>{{ item.transacao.numValTransferencia }}</td>
          <td>{{ item.transacao.numValTaxaPrevista }}</td>
        </tr>
      </tbody>
    </v-table>
  </v-card>
  <router-view></router-view>
</template>

<script>
import { ref, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import { useTransacaoStore } from "@stores/transacaoStore";
import { useClienteStore } from "@stores/clienteStore";
import { useUtils } from "@/models/utils";
import { util } from "prettier";
export default {
  setup() {
    const router = useRouter();
    const transacaoStore = useTransacaoStore();
    const clienteStore = useClienteStore();
    const utils = useUtils();

    const fetchData = async () => {
      await transacaoStore.getAllByClienteResponsavelUuid(
        clienteStore.clienteLogado.uuid,
      );
    };
    const goFromTransacao = () => {
      router.push({ name: "transacao-novo" });
    };

    onMounted(() => {
      fetchData();
    });

    return {
      goFromTransacao,
      transacaoStore,
      utils,
    };
  },
};
</script>

<style lang="scss" scoped></style>
