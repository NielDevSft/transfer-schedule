<template>
  <v-toolbar :elevation="8">
    <v-toolbar-title>
      <div v-if="clienteStore.clienteLogado.uuid">
        Nome: {{ clienteStore.clienteLogado.desNomeCompleto }}
      </div>
      <v-dialog width="500" v-else>
        <template v-slot:activator="{ props }">
          <v-btn v-bind="props" color="success">Ativar conta</v-btn>
        </template>
        <template v-slot:default="{ isActive }">
          <ClienteForm @fecharModal="fecharModal(isActive)"> </ClienteForm>
        </template>
      </v-dialog>
      <div v-if="clienteStore.clienteLogado.uuid">
        Conta:
        {{
          utils.formatarNumeroConta(contaStore.contaCorrente.codConta) ||
          "selecione uma conta"
        }}
        <v-dialog width="500">
          <template v-slot:activator="{ props }">
            <v-btn v-bind="props" color="warning">Trocar de conta</v-btn>
          </template>
          <template v-slot:default="{ isActive }">
            <v-card>
              <v-card-title>
                <span class="text-h5">Selecione uma conta</span>
              </v-card-title>
              <v-card-text>
                <v-select
                  v-model="contaSelecionada"
                  :items="contaStore.contaList"
                  label="Tipo de Operação"
                  item-title="uuid"
                  item-value="uuid"
                ></v-select>
              </v-card-text>
              <v-card-actions>
                <v-btn color="red" v-on:click="isActive = false"
                  >Cancelar</v-btn
                >
                <v-btn color="red" v-on:click="onSelectConta()">
                  Selecionar
                </v-btn>
              </v-card-actions>
            </v-card>
          </template>
        </v-dialog>
      </div>
    </v-toolbar-title>
    <v-toolbar-items>
      <v-btn v-if="clienteStore.clienteLogado.uuid" v-on:click="goTransacao"
        >Transações</v-btn
      >
      <v-btn color="red" v-on:click="onLogout()">Logout</v-btn>
    </v-toolbar-items>
  </v-toolbar>
</template>

<script>
import { useRouter } from "vue-router";
import ClienteForm from "@/components/cliente/ClienteForm.vue";
import { onMounted } from "vue";
import { useAuthenticationStore } from "@stores/authenticationStore";
import { useClienteStore } from "@stores/clienteStore";
import { useContaStore } from "@stores/contaStore";
import { useUtils } from "@/models/utils";
import { ref } from "vue";

export default {
  components: [ClienteForm],
  setup() {
    const userStore = useAuthenticationStore();
    const clienteStore = useClienteStore();
    const router = useRouter();
    const contaStore = useContaStore();
    const utils = useUtils();
    const contaSelecionada = ref("");
    const fecharModal = (isActive) => {
      isActive.value = false;
    };

    const onLogout = async () => {
      userStore.logout();
      router.push({ name: "login" });
    };
    const goTransacao = () => {
      router.push({ name: "transacao" });
    };
    const onSelectConta = () => {
      console.log(contaSelecionada);
      contaStore.setContaCorrete(contaSelecionada.value);
    };

    onMounted(() => {
      contaStore.getContaByClienteUuid(clienteStore.clienteLogado.uuid).then();
    });
    return {
      onLogout,
      goTransacao,
      fecharModal,
      clienteStore,
      contaStore,
      utils,
      onSelectConta,
      contaSelecionada,
    };
  },
};
</script>

<style lang="scss" scoped>
.v-row {
  margin: 0;
}
</style>
