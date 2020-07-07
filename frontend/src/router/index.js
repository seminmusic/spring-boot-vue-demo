import Vue from "vue";
import VueRouter from "vue-router";
// import Home from "../views/Home.vue";
import Home from "@/components/Home";
import NotFound from "@/components/NotFound";
import CarList from "@/components/car/CarList.component";
import CarDetails from "@/components/car/CarDetails.component";
import CarCreate from "@/components/car/CarCreate.component";
import CarUpdate from "@/components/car/CarUpdate.component";

Vue.use(VueRouter);

const routes = [
    {
        path: "/",
        name: "Home",
        component: Home
    },
    {
        path: "/car/list",
        name: "CarList",
        component: CarList
    },
    {
        path: "/car/create",
        name: "CarCreate",
        component: CarCreate
    },
    {
        path: "/car/update/:id",
        name: "CarUpdate",
        component: CarUpdate
    },
    {
        path: "/car/:id",
        name: "CarDetails",
        component: CarDetails
    },
    {
        path: "*",
        name: "NotFound",
        component: NotFound
    }
    // {
    //     path: "/about",
    //     name: "About",
    //     // route level code-splitting
    //     // this generates a separate chunk (about.[hash].js) for this route
    //     // which is lazy-loaded when the route is visited.
    //     component: () => import(/* webpackChunkName: "about" */ "../views/About.vue")
    // }
];

const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes
});

export default router;
