<template>
  <v-card>
    <v-card-title>
      <span class="text-h5">Cadastro de transação</span>
    </v-card-title>
    <v-card-text>
      <v-container>
        <v-form @submit.prevent>
          <v-row>
            <v-col cols="4" sm="6" md="4">
              <v-text-field
                v-model="codTransacao"
                label="Código da Transação"
                disabled=""
              ></v-text-field>
            </v-col>

            <v-col cols="4" sm="6" md="4">
              <v-text-field
                v-model="numValTransferencia"
                label="Valor da Transferência*"
                required
              ></v-text-field>
            </v-col>

            <v-col cols="4" sm="6" md="4">
              <v-text-field
                v-model="numValTaxaPrevista"
                label="Valor da Taxa Prevista*"
                disabled
              ></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="12" sm="6" md="6">
              <v-text-field
                v-model="dtaTransacao"
                label="Data da Transação*"
                type="date"
                required
              ></v-text-field>

              <p>
                Data selecionada: {{ formatDateString(dtaTransacao.value) }}
              </p>
            </v-col>

            <v-col cols="12" sm="6" md="6">
              <v-select
                v-model="indTipoOperacao"
                :items="tipoOperacaoOptions"
                label="Tipo de Operação"
              ></v-select>
            </v-col>

            <v-col cols="12" sm="6" md="6">
              <v-text-field
                v-model="codContaOrigem"
                label="Código da Conta de Origem*"
                disabled
              ></v-text-field>
            </v-col>

            <v-col cols="12" sm="6" md="6">
              <v-text-field
                v-model="codContaDestino"
                label="Código da Conta de Destino*"
                required
              ></v-text-field>
            </v-col>
          </v-row>
        </v-form>
      </v-container>
      <small>*indicates required field</small>
    </v-card-text>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn text="Voltar" color="red" v-on:click="voltar"></v-btn>
      <v-btn color="blue-darken-1" variant="text" v-on:click="criarTransacao()">
        Criar
      </v-btn>
    </v-card-actions>
  </v-card>
</template>
<script>
import { ref, watch } from "vue";
import { Transacao } from "@/models/transacao";
import { useContaStore } from "@stores/contaStore";
import { useTransacaoStore } from "@stores/transacaoStore";
import { useClienteStore } from "@stores/clienteStore";
import { TIPOOPERACAO } from "@/models/transacao";
import { useUtils } from "@models/utils";
import { useRouter } from "vue-router";

export default {
  setup(props) {
    const utils = useUtils();
    const router = useRouter();
    const clienteStore = useClienteStore();
    const transacaoStore = useTransacaoStore();
    const contaStore = useContaStore();

    const formattedDate = ref(null);
    const uuid = ref("");
    const codTransacao = ref("");
    const numValTransferencia = ref("");
    const numValTaxaPrevista = ref("");
    const dtaTransacao = ref("");
    const indTipoOperacao = ref("");
    const codContaOrigem = ref(contaStore.contaCorrente.contaLabel);

    watch(
      () => contaStore.contaCorrente,
      (cc) => {
        codContaOrigem.value = cc.contaLabel;
      },
    );
    watch(
      () => transacaoStore.transacaoList,
      () => {
        router.push({ name: "transacao" });
      },
    );

    const codContaDestino = ref("");

    const tipoOperacaoOptions = ref([
      { title: "A", value: 1 },
      { title: "B", value: 2 },
      { title: "C", value: 3 },
      { title: "D", value: 4 },
    ]);

    const formatDateString = (date) => {
      if (date) return date?.toLocaleDateString();
    };

    const criarTransacao = async () => {
      const tra = new Transacao(
        uuid.value,
        codTransacao.value,
        numValTransferencia.value,
        numValTaxaPrevista.value,
        utils.convertStringToDate(dtaTransacao.value),
        null,
        null,
        clienteStore.clienteLogado.uuid,
        TIPOOPERACAO[indTipoOperacao.value],
      );

      await transacaoStore.createTransacao(
        tra,
        codContaOrigem.value,
        codContaDestino.value,
      );

      await clienteStore.setCliente(contaStore.contaCorrente.cliente);
    };

    const voltar = () => {
      router.push({ name: "transacao" });
    };
    return {
      codTransacao,
      numValTransferencia,
      numValTaxaPrevista,
      indTipoOperacao,
      codContaOrigem,
      codContaDestino,
      tipoOperacaoOptions,
      contaStore,
      formattedDate,
      dtaTransacao,
      formatDateString,
      criarTransacao,
      voltar,
    };
  },
};
</script>
