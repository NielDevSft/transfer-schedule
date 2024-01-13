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
                required
              ></v-text-field>
            </v-col>

            <v-col cols="12" sm="6" md="12">
              <v-text-field
                v-model="desCpf"
                label="CPF*"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12" sm="6" md="12">
              <v-text-field
                v-model="dataNascimento"
                label="Data de nascimento*"
                type="date"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12">
              <v-text-field
                v-model="numTelefone"
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
import { ContaService } from "@services/ContaService";
import Usuario, { Cliente } from "@/models/cliente";

export default {
  setup(props, { emit }) {
    console.log();
    const desNome = ref("");
    const desCpf = ref("");
    const dataNascimento = ref("");
    const numTelefone = ref("");

    const criarConta = async () => {
      const contaService = new ContaService();
      const userWA = JSON.parse(sessionStorage.getItem("userLogged"));

      const cli = new Cliente(
        desCpf.value,
        desNome.value,
        numTelefone.value,
        userWA.user.id,
        {},
        dataNascimento.value,
      );

      try {
        const novaConta = await contaService.createPrimeiraConta(cli);
        emitFecharModal();
      } catch (e) {
        console.log(e);
      }
    };

    const emitFecharModal = () => {
      emit("fecharModal", false); // Emite um evento para o pai
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
