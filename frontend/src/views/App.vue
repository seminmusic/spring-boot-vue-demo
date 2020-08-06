<template>
    <div>
        <b-container fluid="true">
            <navigation-bar :current-user="currentUser" />
        </b-container>
        <b-container class="content">
            <b-row>
                <b-col>
                    <router-view />
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
import Navbar from "@/views/_partials/Navbar";
import { AuthService } from "@/services/auth-service";

export default {
    name: "App",
    components: {
        "navigation-bar": Navbar
    },
    data() {
        return {
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
    }
};
</script>

<style scoped>
.content {
    margin-top: 2rem;
}
</style>
