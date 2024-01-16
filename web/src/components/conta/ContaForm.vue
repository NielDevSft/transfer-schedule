<template>
  <v-card>
    <v-card-title>
      <span class="text-h5">Cadastro de Conta Nova</span>
    </v-card-title>
    <v-card-text>
      <v-container>
        <v-form @submit.prevent>
          <v-row>
            <v-col cols="12" sm="6" md="12">
              <v-text-field
                v-model="desNome"
                label="Nome completo*"
                disabled
              ></v-text-field>
            </v-col>

            <v-col cols="12" sm="6" md="12">
              <v-text-field
                v-model="desCpf"
                label="CPF*"
                disabled
              ></v-text-field>
            </v-col>
            <v-col cols="12" sm="6" md="12">
              <v-text-field
                v-model="dataNascimento"
                label="Data de nascimento*"
                type="date"
                disabled
              ></v-text-field>
            </v-col>
            <v-col cols="12">
              <v-text-field
                v-model="numTelefone"
                label="Número de telefone*"
                disabled
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
import { ref } from "vue";
import { Cliente } from "@/models/cliente";
import { useContaStore } from "@stores/contaStore";
import { useClienteStore } from "@stores/clienteStore";

export default {
  setup(props) {
    const clienteStore = useClienteStore();

    const contaStore = useContaStore();

    const desNome = ref(clienteStore.clienteLogado.desNomeCompleto);
    const desCpf = ref(clienteStore.clienteLogado.desCpf);
    const dataNascimento = ref(
      new Date(clienteStore.clienteLogado.dtaNascimento)
        .toISOString()
        .split("T")[0],
    );
    const numTelefone = ref(clienteStore.clienteLogado.numTelefone);

    const criarConta = async () => {
      await contaStore.createConta(clienteStore.clienteLogado);
    };

    return {
      desNome,
      desCpf,
      dataNascimento,
      numTelefone,
      criarConta,
    };
  },
};
</script>
