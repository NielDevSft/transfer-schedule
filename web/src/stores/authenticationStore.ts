import { Role } from "@/models/role";
import { UserWithAuthorities } from "@/models/userWithAuthorities";
import Usuario from "@/models/usuario";
import { defineStore } from "pinia";
import { AuthenticationService } from "@/services/AuthenticationService";
import { useToast } from "vue-toastification";

const authenticationService = new AuthenticationService();
const toast = useToast();

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
        toast.success("Login feito com sucesso");
      } catch (error) {
        toast.error("Usuário ou senha invalidos");
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
