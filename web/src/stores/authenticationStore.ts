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
      userLogged:
        userAuthenticated ||
        new UserWithAuthorities(new Usuario(0, "", ""), [new Role(0, "")]),
    };
  },
  actions: {
    async login(userName: string, passWord: string) {
      try {
        const response = await authenticationService.login(userName, passWord);
        this.userLogged = response.data;
        sessionStorage.setItem("userLogged", JSON.stringify(response.data));
      } catch (error) {
        console.error("Erro ao buscar dados:", error);
      }
    },
    logout() {
      this.userLogged = new UserWithAuthorities(new Usuario(0, "", ""), [
        new Role(0, ""),
      ]);
      sessionStorage.clear();
    },
  },

  getters: {
    rolesList(): string[] {
      return this.userLogged.authority.map((rol) => rol.authority);
    },
  },
});
