import Vue from "vue";
import App from "@/views/App.vue";
import router from "@/router/router";
import BootstrapVue from "bootstrap-vue";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import Axios from "axios";
import { AppConstants } from "@/models/constants/app-constants";


Vue.config.productionTip = false;

// Setup Axios
Axios.defaults.baseURL = process.env.VUE_APP_API_ENDPOINT;
if (localStorage.getItem(AppConstants.AUTH_TOKEN_STORAGE_KEY)) {
    Axios.defaults.headers.common.Authorization = `Bearer ${localStorage.getItem(AppConstants.AUTH_TOKEN_STORAGE_KEY)}`;
}

Vue.use(BootstrapVue);

new Vue({
    router,
    render: h => h(App)
}).$mount("#app");

console.log(process.env);
