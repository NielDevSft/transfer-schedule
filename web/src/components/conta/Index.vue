<template>
  <v-card>
    <v-card-title class="d-flex justify-space-between align-center">
      Contas
      <v-btn
        class="ml-2 text-end"
        left
        color="success"
        size="small"
        v-on:click="goNovaConta()"
      >
        <v-icon icon="$plus" size="x-small"></v-icon>
        Criar Nova Conta</v-btn
      >
    </v-card-title>
    <v-table theme="dark">
      <thead>
        <tr>
          <th class="text-left">Número da conta</th>
          <th class="text-left">Nome do Responsável</th>
          <th class="text-left">CPF do Responsável</th>
          <th class="text-left">Data da Criação</th>
          <th class="text-left"></th>
        </tr>
      </thead>
      <tbody v-if="!!contaStore.contaList.length">
        <tr v-for="item in contaStore.contaList" :key="item.conta.uuid">
          <td>{{ item.conta.contaLabel }}</td>
          <td>{{ item.conta.cliente.desNomeCompleto }}</td>
          <td>{{ item.conta.cliente.desCpf }}</td>
          <td>{{ toShortDate(item.conta.dtaCreateAt) }}</td>
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
import { useContaStore } from "@stores/contaStore";
import { useClienteStore } from "@stores/clienteStore";
export default {
  setup() {
    const router = useRouter();
    const contaStore = useContaStore();
    const clienStore = useClienteStore();
    const data = ref([]);

    const fetchData = async () => {
      contaStore.getContaByClienteUuid(clienStore.clienteLogado.uuid);
    };
    const goNovaConta = () => {
      router.push({ name: "conta-novo" });
    };

    onMounted(() => {
      fetchData();
    });

    const toShortDate = (date) => {
      return new Date(new String(date)).toLocaleDateString();
    };

    return {
      contaStore,
      goNovaConta,
      toShortDate,
    };
  },
};
</script>

<style lang="scss" scoped></style>
