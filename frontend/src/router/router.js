import Vue from "vue";
import VueRouter from "vue-router";
import Home from "@/views/home/Home";
import NotFound from "@/views/_common/NotFound";
import Cars from "@/views/car/Cars";
import CarDetails from "@/views/car/components/CarDetails.component";
import CarCreate from "@/views/car/components/CarCreate.component";
import CarUpdate from "@/views/car/components/CarUpdate.component";
import Login from "@/views/login/Login";
import { AuthService } from "@/services/auth-service";
import { Role } from "@/models/constants/role-constants";


Vue.use(VueRouter);

const routes = [
    {
        path: "/",
        name: "Home",
        component: Home
    },
    {
        path: "/login",
        name: "Login",
        component: Login
    },
    {
        path: "/cars",
        name: "Cars",
        component: Cars,
        meta: {
            authorize: [Role.VIEW]
        }
    },
    {
        path: "/cars/create",
        name: "CarCreate",
        component: CarCreate
    },
    {
        path: "/cars/update/:id",
        name: "CarUpdate",
        component: CarUpdate
    },
    {
        path: "/cars/:id",
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

router.beforeEach((to, from, next) => {
    // console.log("to", to);
    // console.log("from", from);

    // Route access check
    const { authorize } = to.meta;
    if (authorize) {
        // Authentication check
        const userAuthenticated = AuthService.userAuthenticated();
        if (!userAuthenticated) {
            // User not logged in - redirect to login page with return URL as query parameter
            return next({ path: "/login", query: { redirectReasonCode: 1, returnUrl: to.fullPath } });
        }
        // Roles check
        const userData = AuthService.userData();
        if (authorize.length) {
            const userRoles = userData.roles;
            if (!userRoles || !userRoles.length) {
                // Route requires role(s) but user don't have any role(s)
                return next({ path: "/login", query: { redirectReasonCode: 2, returnUrl: to.fullPath } });
            }
            if (!authorize.some(role => userRoles.includes(role))) {
                // User don't have any of route required role(s)
                return next({ path: "/login", query: { redirectReasonCode: 3, returnUrl: to.fullPath } });
            }
        }
    }

    next();
});

export default router;
