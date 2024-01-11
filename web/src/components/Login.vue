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
                v-model="email"
                :rules="rules"
                label="Email"
              ></v-text-field>
              <v-text-field
                v-model="password"
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
            <v-btn v-if="isLogin" type="submit" class="mt-2">Login</v-btn>
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
import { ref, reactive } from "vue";

export default {
  setup() {
    return {
      isLogin: ref(true),
    };
  },
};
</script>

<style lang="scss" scoped></style>
