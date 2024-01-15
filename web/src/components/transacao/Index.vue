<template>
  <v-card>
    <v-card-title class="d-flex justify-space-between align-center">
      Trasações agendadas
      <v-btn
        class="ml-2 text-end"
        left
        color="success"
        size="small"
        v-on:click="goNovoUsuario()"
      >
        <v-icon icon="$plus" size="x-small"></v-icon>
        Agendar transação</v-btn
      >
    </v-card-title>
    <v-table theme="dark">
      <thead>
        <tr>
          <th class="text-left">#</th>
          <th class="text-left">Nome</th>
          <th class="text-left">Nun. Conta Origem</th>
          <th class="text-left">Nun. Conta Destido</th>
          <th class="text-left">Valor transação</th>
          <th class="text-left">Taxa prevista</th>
          <th class="text-left"></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in data" :key="item.uuid">
          <td>{{ item.codTransacao }}</td>
          <td>{{ item.desNome }}</td>
          <td>{{ item.numContaOrigem }}</td>
          <td>{{ item.numContaDestino }}</td>
          <td>{{ item.numValTransferencia }}</td>
          <td>{{ item.numValTaxaPrevista }}</td>
          <dt><v-btn></v-btn></dt>
        </tr>
      </tbody>
    </v-table>
  </v-card>
  <router-view></router-view>
</template>

<script>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useTransacaoStore } from "@stores/transacaoStore";
export default {
  setup() {
    const router = useRouter();
    const transacaoStore = useTransacaoStore();
    const data = ref(transacaoStore.transacaoList.map((tra) => tra.transacao));

    const fetchData = async () => {
      transacaoStore.getAll();
    };
    const goNovoUsuario = () => {
      console.log("chegou");
      router.push({ name: "transacao-novo" });
    };

    onMounted(() => {
      fetchData();
    });

    return {
      data,
      goNovoUsuario,
    };
  },
};
</script>

<style lang="scss" scoped></style>
