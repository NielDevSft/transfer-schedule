<template>
  <v-container class="fill-height">
    <v-responsive class="align-center text-center fill-height">
      <v-sheet width="300" class="mx-auto">
        <v-card>
          <v-card-title>{{
            isLogin ? "Faça seu login" : "Criar usuário"
          }}</v-card-title>
          <v-card-text v-if="isLogin">
            <v-form @submit.prevent>
              <v-text-field
                v-model="emailLogin"
                :rules="rules"
                label="Email"
              ></v-text-field>
              <v-text-field
                v-model="passwordLogin"
                :rules="rules"
                label="Senha"
              ></v-text-field>
            </v-form>
          </v-card-text>

          <v-card-text v-else>
            <v-form @submit.prevent>
              <v-text-field
                v-model="email"
                :rules="rules"
                label="Email"
              ></v-text-field>
              <v-text-field
                v-model="password"
                :rules="rules"
                label="Senha"
              ></v-text-field>
              <v-text-field
                v-model="passwordConfirmation"
                :rules="rules"
                label="Confirme a Senha"
              ></v-text-field>
            </v-form>
          </v-card-text>

          <v-card-actions v-if="isLogin">
            <v-btn
              v-if="isLogin"
              type="submit"
              v-on:click="onLogin()"
              class="mt-2"
              >Login</v-btn
            >
            <v-btn type="submit" class="mt-2" v-on:click="isLogin = !isLogin"
              >Criar usuario</v-btn
            >
          </v-card-actions>
          <v-card-actions v-else>
            <v-btn type="submit" class="mt-2" v-on:click="isLogin = !isLogin"
              >Cancelar</v-btn
            >
            <v-btn type="submit" class="mt-2">Criar usuario</v-btn>
          </v-card-actions>
        </v-card>
      </v-sheet>
    </v-responsive>
  </v-container>
</template>

<script>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useAuthenticationStore } from "@/stores/authenticationStore";

export default {
  setup() {
    const userStore = useAuthenticationStore();
    const router = useRouter();
    const isLogin = ref(true);
    const emailLogin = ref("");
    const passwordLogin = ref("");

    const rules = ref([(v) => !!v || "Campo obrigatório"]);

    const onLogin = async () => {
      await userStore.login(emailLogin.value, passwordLogin.value);
      router.push({ name: "main" });
    };

    return {
      isLogin,
      emailLogin,
      passwordLogin,
      onLogin,
      rules,
    };
  },
};
</script>

<style lang="scss" scoped></style>
