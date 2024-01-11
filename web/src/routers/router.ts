// router.ts
import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import Login from "../components/Login.vue";
import Transasao from "../components/Transacao/Index.vue";
import FormModal from "../components/Transacao/FormModal.vue";
import MainContainer from "@/components/commom/MainContainer.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/login",
    component: Login,
  },
  {
    path: "/",
    component: MainContainer,
    children: [
      {
        path: "transacao",
        component: Transasao,
        children: [
          {
            path: "/novo",
            component: FormModal,
          },
          {
            path: "/edit",
            component: FormModal,
          },
        ],
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
