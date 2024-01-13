<template>
  <v-card>
    <v-card-title class="d-flex justify-space-between align-center">
      Trasações agendadas
      <v-btn class="ml-2 text-end" left color="success" size="small">
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
</template>

<script>
import { ref, onMounted } from "vue";
import { TransacaoService } from "@/services/TransacaoService";

export default {
  setup() {
    const data = ref([]);

    const fetchData = async () => {
      const traService = new TransacaoService();
      try {
        const response = await traService.getAll();
        data.value = response.data;
      } catch (error) {
        console.error("Erro ao buscar dados:", error);
      }
    };

    onMounted(() => {
      fetchData();
    });

    return {
      data,
    };
  },
};
</script>

<style lang="scss" scoped></style>
