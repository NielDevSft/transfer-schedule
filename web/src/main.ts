// Plugins
import { registerPlugins } from "@/plugins";

// Components
import App from "./App.vue";

// Composables
import { createApp } from "vue";
import { createPinia, PiniaVuePlugin } from "pinia";
import Toast from "vue-toastification";

import "vue-toastification/dist/index.css";
import router from "./routers/router";

const options = {
  position: "top-right",
  timeout: 5000,
  closeOnClick: true,
  pauseOnFocusLoss: true,
  pauseOnHover: true,
  draggable: true,
  draggablePercent: 0.6,
  showCloseButtonOnHover: false,
  hideProgressBar: true,
  closeButton: "button",
  icon: true,
  rtl: false,
};

const pinia = createPinia();
const app = createApp(App);
app.use(PiniaVuePlugin);
app.use(pinia);
app.use(router);
app.use(Toast, options);

registerPlugins(app);

app.mount("#app");
