<template>
  <v-card>
    <v-card-title>
      <span class="text-h5">Cadastro de Cliente</span>
    </v-card-title>
    <v-card-text>
      <v-container>
        <v-form @submit.prevent>
          <v-row>
            <v-col cols="12" sm="6" md="12">
              <v-text-field
                v-model="desNome"
                label="Nome completo*"
                value="Daniel Figuieredo"
                required
              ></v-text-field>
            </v-col>

            <v-col cols="12" sm="6" md="12">
              <v-text-field
                v-model="desCpf"
                label="CPF*"
                :value="47995274826"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12" sm="6" md="12">
              <v-text-field
                v-model="dataNascimento"
                label="Data de nascimento*"
                value="1999-08-02"
                type="date"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12">
              <v-text-field
                v-model="numTelefone"
                value="11973829618"
                label="Número de telefone*"
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
import { Cliente } from "@/models/cliente";
import { useContaStore } from "@stores/contaStore";
import { useClienteStore } from "@stores/clienteStore";
import { useAuthenticationStore } from "@/stores/authenticationStore";
import { useUtils } from "@/models/utils";

export default {
  setup(props, { emit }) {
    const utils = useUtils();
    const clienteStore = useClienteStore();
    const authenticationStore = useAuthenticationStore();
    const constaStore = useContaStore();

    const desNome = ref("");
    const desCpf = ref("");
    const dataNascimento = ref("");
    const numTelefone = ref("");

    const criarConta = async () => {
      const cli = new Cliente(
        desNome.value,
        desCpf.value,
        utils.convertStringToDate(dataNascimento.value),
        authenticationStore.userCorrente,
      );
      await constaStore.createPrimeiraConta(cli);

      await clienteStore.setCliente(constaStore.contaCorrente.cliente);
      emitFecharModal();
    };

    const emitFecharModal = () => {
      emit("fecharModal", false);
    };
    return {
      desNome,
      desCpf,
      dataNascimento,
      numTelefone,
      criarConta,
      emitFecharModal,
    };
  },
};
</script>
