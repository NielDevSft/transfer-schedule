// router.ts
import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import Login from "../components/Login.vue";
import Transasao from "../components/Transacao/Index.vue";
import Form from "../components/Transacao/Form.vue";
import MainContainer from "@/components/commom/MainContainer.vue";
import { UserWithAuthorities } from "@/models/userWithAuthorities";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "main",
    component: MainContainer,
    meta: { roles: ["ROLE_USER", "ROLE_ADMIN"] },
    children: [
      {
        path: "transacao",
        name: "transacao",
        component: Transasao,
        meta: { roles: ["ROLE_USER", "ROLE_ADMIN"] },
        children: [
          {
            path: "/novo",
            name: "transacao/novo",
            component: Form,
            meta: { roles: ["ROLE_USER", "ROLE_ADMIN"] },
          },
          {
            path: "/edit",
            component: Form,
            meta: { roles: ["ROLE_USER", "ROLE_ADMIN"] },
          },
        ],
      },
    ],
  },
  {
    path: "/login",
    name: "login",
    component: Login,
  },
  {
    path: "/:catchAll(.*)",
    redirect: "/",
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const userRoles = (
    JSON.parse(
      sessionStorage.getItem("userLogged") || "null",
    ) as UserWithAuthorities
  )?.authority;
  console.log(userRoles);

  if (to.path === "/login") {
    if (userRoles && userRoles.length) {
      next("/");
    }
  }

  if (!to.meta || !to.meta.roles || (to.meta.roles as string[]).length === 0) {
    next();
  } else if (
    userRoles &&
    userRoles.length &&
    userRoles.some((userRole) =>
      (to.meta.roles as string[]).includes(userRole.authority),
    )
  ) {
    // Se as roles do usuário contêm alguma das roles da rota, permitir o acesso
    next();
  } else {
    // Se não houver correspondência de roles, redirecionar para login
    next("/login");
  }
});
export default router;
