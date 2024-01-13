<template>
  <v-toolbar :elevation="8">
    <v-toolbar-title>
      <div v-if="cliente">Bem vindo:{{ cliente.desNome }}</div>
      <v-dialog width="500" v-else>
        <template v-slot:activator="{ props }">
          <v-btn v-bind="props" color="success">Ativar conta</v-btn>
        </template>
        <template v-slot:default="{ isActive }">
          <ClienteForm @fecharModal="fecharModal(isActive)"> </ClienteForm>
        </template>
      </v-dialog>
    </v-toolbar-title>
    <v-toolbar-items>
      <v-btn v-if="cliente" v-on:click="goTransacao">Transações</v-btn>
      <v-btn color="red" v-on:click="onLogout()">Logout</v-btn>
    </v-toolbar-items>
  </v-toolbar>
</template>

<script>
import { useRouter } from "vue-router";
import ClienteForm from "@/components/cliente/ClienteForm.vue";
import { useAuthenticationStore } from "@stores/authenticationStore";

export default {
  components: [ClienteForm],
  setup() {
    const userStore = useAuthenticationStore();
    const router = useRouter();

    const cliente = JSON.parse(sessionStorage.getItem("clienteLogged"));

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
    return {
      onLogout,
      goTransacao,
      fecharModal,
      cliente,
    };
  },
};
</script>

<style lang="scss" scoped></style>
