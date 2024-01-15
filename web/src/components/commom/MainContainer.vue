<template>
  <ToolBar />
  <v-container>
    <v-responsive>
      <router-view></router-view>
    </v-responsive>
  </v-container>
</template>

<script>
import { useClienteStore } from "@stores/clienteStore";
import { onMounted } from "vue";
import { useAuthenticationStore } from "@/stores/authenticationStore";

export default {
  setup() {
    const clienteStore = useClienteStore();
    const authStore = useAuthenticationStore();

    onMounted(() => {
      clienteStore.getClienteByUsuario(authStore.userStore.user.id);
    });

    return {};
  },
};
</script>

<style lang="scss" scoped>
.v-container {
  max-width: 900px;
}
</style>
