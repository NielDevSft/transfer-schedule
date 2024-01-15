import { Role } from "@/models/role";
import { UserWithAuthorities } from "@/models/userWithAuthorities";
import Usuario from "@/models/usuario";
import { defineStore } from "pinia";
import { AuthenticationService } from "@/services/AuthenticationService";
const authenticationService = new AuthenticationService();

export const useAuthenticationStore = defineStore("authentication", {
  state() {
    const userAuthenticated = JSON.parse(
      sessionStorage.getItem("userLogged") || "null",
    ) as UserWithAuthorities;

    return {
      userStore:
        userAuthenticated ||
        new UserWithAuthorities(initUsuario, [new Role(0, "")]),
    };
  },
  actions: {
    async login(userName: string, passWord: string) {
      try {
        const response = await authenticationService.login(userName, passWord);
        this.userStore = response.data;
        sessionStorage.setItem("userLogged", JSON.stringify(response.data));
      } catch (error) {
        console.error("Erro ao buscar dados:", error);
      }
    },
    logout() {
      this.userStore = new UserWithAuthorities(initUsuario, [new Role(0, "")]);
      sessionStorage.clear();
    },
  },

  getters: {
    rolesList(): string[] {
      return this.userStore.authority.map((rol) => rol.authority);
    },
    userCorrente(): Usuario {
      return this.userStore.user;
    },
  },
});
const initUsuario = new Usuario(0, "", "", true);
