<template>
    <v-app>
        <v-navigation-drawer app v-model="navigationDrawer" color="primary" dark class="navigation-drawer">
            <v-list dense nav class="py-0 nav-list">
                <v-list-item class="app-title-wrapper my-5">
                    <v-list-item-avatar>
                        <img src="@/assets/images/car-icon.png" class="app-logo">
                    </v-list-item-avatar>
                    <v-list-item-content>
                        <v-list-item-title>
                            Sema Cars Management
                        </v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
                <v-divider class="mb-5 app-title-divider" />
                <v-list-item link :to="{ name: 'Home' }" exact>
                    <v-list-item-action>
                        <v-icon>mdi-home</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title>Home</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
                <v-list-item link :to="{ name: 'Cars' }" v-if="userHasAnyRole(Role.VIEW)">
                    <v-list-item-action>
                        <v-icon>mdi-car-multiple</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title>Cars</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
            </v-list>
        </v-navigation-drawer>
        <v-app-bar app dark dense class="app-bar" hide-on-scroll>
            <v-app-bar-nav-icon @click.stop="navigationDrawer = !navigationDrawer"></v-app-bar-nav-icon>
            <v-spacer />
            <template v-if="currentUser">
                <div class="text-body-2 mr-2 d-none d-sm-block">music.semin@gmail.com</div>
                <v-avatar size="34px" class="mr-2">
                    <img src="@/assets/images/user.jpg">
                </v-avatar>
                <v-btn text small outlined class="app-bar-btn-icon-text" @click="logout">
                    <span>Logout</span>
                    <v-icon right class="ml-1">mdi-power</v-icon>
                </v-btn>
            </template>
            <v-btn text small outlined class="app-bar-btn-icon-text" :to="{ name: 'Login' }" v-else>
                <v-icon left>mdi-account-circle</v-icon>
                <span>Login</span>
            </v-btn>
        </v-app-bar>
        <v-main>
            <v-container fluid :class="{'content-center': contentCenter}">
                <router-view />
            </v-container>
        </v-main>
    </v-app>
</template>


<script>
import { AuthService } from "@/services/auth-service";
import { Role } from "@/models/constants/role-constants";

export default {
    name: "App",
    data() {
        return {
            navigationDrawer: null,
            contentCenter: false,
            Role: Role,
            currentUser: null
        };
    },
    created() {
        this.userDataSubscription = AuthService.userDataObservable.subscribe(u => {
            this.currentUser = u;
        });
        handleRoute.call(this, this.$route);
    },
    beforeDestroy() {
        this.userDataSubscription.unsubscribe();
    },
    watch: {
        $route(to, from) {
            handleRoute.call(this, to);
        }
    },
    methods: {
        userHasAnyRole(...roles) {
            return AuthService.userHasAnyRole(...roles);
        },
        logout() {
            AuthService.logout();
            this.$router.push("/").catch(() => {});
        }
    }
};

// Private functions
function handleRoute(route) {
    if (route.meta && route.meta.contentCenter) {
        this.contentCenter = true;
    }
}
</script>


<style scoped>
.navigation-drawer {
    background: #1A2980;  /* Fallback for old browsers */
    background: -webkit-linear-gradient(to right, #1A2980, #3E99A7);  /* Chrome 10-25, Safari 5.1-6 */
    background: linear-gradient(to right, #1A2980, #3E99A7); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
}
.app-title-wrapper {
    flex-direction: column;
}
.app-title-wrapper .v-avatar {
    margin: 0 !important;
    width: 50px !important;
    height: 50px !important;
    background-color: white;
    justify-content: center;
}
.app-title-wrapper .v-list-item__content {
    padding: 0 !important;
    padding-top: 12px !important;
}
.app-title-wrapper::after {
    min-height: unset;
}
.app-logo {
    width: 40px;
    height: 40px;
}
.app-title-wrapper .v-list-item__title {
    font-size: 1.15rem !important;
    line-height: 1.2 !important;
    font-weight: 400 !important;
    white-space: normal;
}
.app-title-divider {
    border-color: rgba(255, 255, 255, 0.3) !important;
}
.nav-list a.v-list-item {
    padding: 0;
    padding-left: 16px !important;
    padding-right: 16px !important;
}
.nav-list a.v-list-item .v-list-item__action {
    margin-right: 20px !important;
}
.nav-list a.v-list-item .v-list-item__title {
    font-weight: 400;
}
.app-bar {
    background: #3E99A7;  /* Fallback for old browsers */
    background: -webkit-linear-gradient(to right, #3E99A7, #1A2980);  /* Chrome 10-25, Safari 5.1-6 */
    background: linear-gradient(to right, #3E99A7, #1A2980); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
    z-index: 7 !important;
}
.navigation-drawer.v-navigation-drawer--is-mobile ~ .app-bar {
    z-index: 5 !important;
}
/* .navigation-drawer.v-navigation-drawer--close ~ .app-bar {
    left: 0 !important;
}
.navigation-drawer.v-navigation-drawer--open ~ .app-bar {
    left: 255px !important;
} */
.app-bar-btn-icon-text {
    font-weight: 400;
}
.app-bar-btn-icon-text.v-btn:not(.v-btn--round).v-size--small {
    height: 27px;
}
.app-bar-btn-icon-text.theme--dark.v-btn.v-btn--outlined.v-btn--text {
    border-color: rgba(255, 255, 255, 0.3);
}
.content-center {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
}
</style>
