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
                required
              ></v-text-field>
            </v-col>

            <v-col cols="12" sm="6" md="6">
              <v-text-field
                v-model="codDestinoOrigem"
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
      <v-btn text="Cancelar" color="red" v-on:click="emitFecharModal"></v-btn>
      <v-btn color="blue-darken-1" variant="text" v-on:click="criarConta()">
        Criar
      </v-btn>
    </v-card-actions>
  </v-card>
</template>
<script>
import { ref, defineEmits } from "vue";
import { Transacao } from "@/models/transacao";
import { useContaStore } from "@stores/contaStore";
import { useTransacaoStore } from "@stores/transacaoStore";
import { useClienteStore } from "@stores/clienteStore";
import { TIPOOPERACAO } from "@/models/transacao";
import { useUtils } from "@models/utils";

export default {
  setup(props, { emit }) {
    const utils = useUtils();
    const clienteStore = useClienteStore();
    const transacaoStore = useTransacaoStore();

    const uuid = ref("");
    const codTransacao = ref("");
    const numValTransferencia = ref("");
    const numValTaxaPrevista = ref("");
    const dtaTransacao = ref("");
    const indTipoOperacao = ref("");
    const codContaOrigem = ref("");
    const codDestinoOrigem = ref("");

    const tipoOperacaoOptions = ["A", "B", "C", "D"];

    const criarConta = async () => {
      const tra = new Transacao(
        uuid.value,
        codTransacao.value,
        numValTransferencia.value,
        numValTaxaPrevista.value,
        utils.convertStringToDate(dtaTransacao.value),
        TIPOOPERACAO[indTipoOperacao.value],
        codContaOrigem.value,
        codDestinoOrigem.value,
        clienteStore.clienteLogado.uuid,
      );
      await transacaoStore.createTransacao(tra);

      await clienteStore.setCliente(constaStore.contaCorrente.cliente);
      emitFecharModal();
    };

    const emitFecharModal = () => {
      emit("fecharModal", false);
    };
    return {
      codTransacao,
      numValTransferencia,
      numValTaxaPrevista,
      indTipoOperacao,
      codContaOrigem,
      codDestinoOrigem,
      tipoOperacaoOptions,
      criarConta,
      emitFecharModal,
    };
  },
};
</script>
