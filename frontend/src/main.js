import Vue from "vue";
import App from "@/views/App.vue";
import router from "@/router";
import BootstrapVue from "bootstrap-vue";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import Axios from "axios";

Vue.config.productionTip = false;

Axios.defaults.baseURL = process.env.VUE_APP_API_ENDPOINT;

Vue.use(BootstrapVue);

new Vue({
    router,
    render: h => h(App)
}).$mount("#app");

console.log(process.env);
