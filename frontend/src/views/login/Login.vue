<template>
    <b-container fluid>
        <b-row align-h="center">
            <b-col cols="4">
                <b-card>
                    <b-form @submit.prevent="onLoginSubmit">
                        <b-alert show variant="warning" v-if="infoMessage" class="text-center mb-3">{{ infoMessage }}</b-alert>
                        <b-form-group label="Username:" for="username">
                            <b-input id="username" type="text" v-model="loginData.username" required />
                        </b-form-group>
                        <b-form-group label="Password:" for="password">
                            <b-input id="password" type="password" v-model="loginData.password" required />
                        </b-form-group>
                        <b-button type="submit" class="login-btn mt-4" block variant="info" :disabled="loading">
                            <span class="spinner-border spinner-border-sm mr-3" v-show="loading"></span>
                            <span>{{ loading ? 'Please wait' : 'Login' }}</span>
                        </b-button>
                        <div v-if="errorMessage" class="text-center mt-3 text-danger">{{ errorMessage }}</div>
                    </b-form>
                </b-card>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
import { AuthService } from "@/services/auth-service";

export default {
    name: "Login",
    data() {
        return {
            loginData: {
                username: "",
                password: ""
            },
            infoMessage: "",
            errorMessage: "",
            returnUrl: "",
            loading: false
        };
    },
    created() {
        parseRouteData.call(this, this.$route.query);
    },
    watch: {
        $route(to, from) {
            parseRouteData.call(this, to.query);
        }
    },
    methods: {
        onLoginSubmit() {
            this.loading = true;
            AuthService.login(this.loginData).then(response => {
                console.log("Redirecting after successful login to: " + this.returnUrl);
                this.$router.push(this.returnUrl).catch(() => {});
            }).catch(error => {
                console.error("Login error", error);
                this.errorMessage = (error && error.data && error.data.message) ? error.data.message : error;
            }).finally(() => {
                this.loading = false;
            });
        }
    }
};

// Private functions
function parseRouteData(query) {
    // Set redirect reason message
    const redirectReasonCode = query.redirectReasonCode;
    if (redirectReasonCode) {
        if (+redirectReasonCode === 1) {
            this.infoMessage = "Please sign-in to access requested page.";
        }
        if (+redirectReasonCode === 2) {
            this.infoMessage = "Your authentication token is expired.";
        }
        if (+redirectReasonCode === 3) {
            this.infoMessage = "Route requires roles but you don't have any role.";
        }
        if (+redirectReasonCode === 4) {
            this.infoMessage = "You don't have any of route required roles.";
        }
    }

    // Set return URL from route parameters or default to "/"
    this.returnUrl = query.returnUrl || "/";
}
</script>

<style scoped>
.login-btn {
    display: flex;
    justify-content: center;
    align-items: center;
    text-transform: uppercase;
}
</style>
