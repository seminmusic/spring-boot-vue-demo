<template>
    <b-navbar toggleable="md" type="dark" variant="info">
        <b-navbar-brand :to="{ name: 'Home' }" active-class="active">Sema Cars</b-navbar-brand>
        <b-navbar-nav>
            <b-nav-item :to="{ name: 'Cars' }" active-class="active" v-if="userHasAnyRole(Role.VIEW)">Cars</b-nav-item>
        </b-navbar-nav>
        <b-navbar-nav class="ml-auto">
            <template v-if="currentUser">
                <b-nav-text>{{ currentUser.email }}</b-nav-text>
                <b-nav-item @click="logout">Logout</b-nav-item>
            </template>
            <template v-else>
                <b-nav-item :to="{ name: 'Login' }" active-class="active">Login</b-nav-item>
            </template>
        </b-navbar-nav>
    </b-navbar>
</template>

<script>
import { AuthService } from "@/services/auth-service";
import { Role } from "@/models/constants/role-constants";

export default {
    props: ["currentUser"],
    data() {
        return {
            Role: Role
        };
    },
    created() {
        // console.log("currentUser", this.currentUser);
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
