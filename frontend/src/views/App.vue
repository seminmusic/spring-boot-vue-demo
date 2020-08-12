<template>
    <v-app>
        <v-navigation-drawer app v-model="navigationDrawer" color="primary" dark>
            <v-list dense nav class="py-0">
                <v-list-item class="mt-1 mb-0">
                    <v-list-item-avatar>
                        <img v-if="currentUser" src="@/assets/images/user.jpg">
                        <v-icon v-else x-large>mdi-account-circle</v-icon>
                    </v-list-item-avatar>
                    <v-list-item-content>
                        <v-list-item-title>{{ currentUser ? currentUser.email : 'Please sign-in' }}</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
                <v-list-item link :to="{ name: 'Home' }" exact class="px-4">
                    <v-list-item-action class="mr-5">
                        <v-icon>mdi-home</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title>Home</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
                <v-list-item link :to="{ name: 'Cars' }" class="px-4" v-if="userHasAnyRole(Role.VIEW)">
                    <v-list-item-action class="mr-5">
                        <v-icon>mdi-car-multiple</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title>Cars</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
                <v-divider></v-divider>
                <v-list-item link :to="{ name: 'Login' }" class="px-4" v-if="!currentUser">
                    <v-list-item-action class="mr-5">
                        <v-icon>mdi-login-variant</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title>Login</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
                <v-list-item link class="px-4" v-else @click="logout">
                    <v-list-item-action class="mr-5">
                        <v-icon>mdi-power</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title>Logout</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
            </v-list>
        </v-navigation-drawer>
        <v-app-bar app>

        </v-app-bar>
        <v-main>
            <v-container fluid>
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
            Role: Role,
            currentUser: null
        };
    },
    created() {
        this.userDataSubscription = AuthService.userDataObservable.subscribe(u => {
            this.currentUser = u;
        });
    },
    beforeDestroy() {
        this.userDataSubscription.unsubscribe();
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
</script>


<style scoped>

</style>
