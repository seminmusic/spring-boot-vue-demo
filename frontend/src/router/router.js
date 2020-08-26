import Vue from "vue";
import VueRouter from "vue-router";
import Home from "@/views/home/Home";
import NotFound from "@/views/_common/NotFound";
import CarDetails from "@/views/car/components/CarDetails.component";
import Login from "@/views/login/Login";
import { AuthService } from "@/services/auth-service";
import { Role } from "@/models/constants/role-constants";


Vue.use(VueRouter);

const routes = [
    {
        path: "/",
        name: "Home",
        component: Home,
        meta: {
            contentCenter: true
        }
    },
    {
        path: "/login",
        name: "Login",
        component: Login,
        meta: {
            contentCenter: true
        }
    },
    {
        path: "/cars",
        name: "Cars",
        component: () => import(/* webpackChunkName: "cars-chunk" */ "@/views/car/Cars.vue"),
        meta: {
            authorize: [Role.VIEW]
        }
    },
    {
        path: "/cars/:id",
        name: "CarDetails",
        component: CarDetails,
        meta: {
            authorize: [Role.VIEW]
        }
    },
    {
        path: "*",
        name: "NotFound",
        component: NotFound,
        meta: {
            contentCenter: true
        }
    }
];

const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes
});

router.beforeEach((to, from, next) => {
    // Dispatch current user data to observable subscribers
    AuthService.nextUserData();

    // Route access check
    const { authorize } = to.meta;
    if (authorize) {
        // Authentication check
        if (!AuthService.userHasToken()) {
            // User not logged in
            return next({ path: "/login", query: { redirectReasonCode: 1, returnUrl: to.fullPath } });
        }
        if (AuthService.userTokenExpired()) {
            // JWT token is expired
            return next({ path: "/login", query: { redirectReasonCode: 2, returnUrl: to.fullPath } });
        }
        // Roles check
        if (authorize.length) {
            const userRoles = AuthService.userData().roles;
            if (!userRoles || !userRoles.length) {
                // Route requires roles but user don't have any role
                return next({ path: "/login", query: { redirectReasonCode: 3, returnUrl: to.fullPath } });
            }
            if (!authorize.some(role => userRoles.includes(role))) {
                // User don't have any of route required roles
                return next({ path: "/login", query: { redirectReasonCode: 4, returnUrl: to.fullPath } });
            }
        }
    }

    next();
});

export default router;
