<template>
    <v-row align="center" justify="center">
        <v-col cols="12" sm="8" md="4">
            <v-card class="elevation-10">
                <v-form @submit.prevent="onLoginSubmit" autocomplete="off">
                    <v-toolbar color="primary" dark flat dense rounded="t">
                        <v-spacer />
                        <v-toolbar-title>Login</v-toolbar-title>
                        <v-spacer />
                    </v-toolbar>
                    <v-card-text>
                        <v-alert type="warning" dense v-if="infoMessage">{{ infoMessage }}</v-alert>
                        <v-alert type="error" dense v-if="errorMessage">{{ errorMessage }}</v-alert>
                        <v-text-field type="text" label="Username" prepend-icon="mdi-account" v-model="loginData.username" />
                        <v-text-field type="password" class="fix-border" label="Password" prepend-icon="mdi-lock" v-model="loginData.password" />
                    </v-card-text>
                    <v-card-actions class="px-4 pt-0 pb-4">
                        <v-spacer />
                        <v-btn type="submit" color="primary" class="btn-loading-with-text" ripple :loading="loading" :disabled="loading">
                            <v-icon left v-if="!loading">mdi-login-variant</v-icon> {{ loading ? 'Please wait' : 'Login' }}
                        </v-btn>
                        <v-spacer />
                    </v-card-actions>
                </v-form>
            </v-card>
        </v-col>
    </v-row>
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
            this.errorMessage = "";
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
.btn-loading-with-text {
    display: flex;
}
.btn-loading-with-text.v-btn--loading /deep/ .v-btn__content {
    opacity: 1;
    order: 2;
}
.btn-loading-with-text.v-btn--loading /deep/ .v-btn__loader {
    position: relative;
    height: auto;
    order: 1;
    margin-left: 4px;
    margin-right: 8px;
    height: 18px;
    width: 18px;
}

.fix-border /deep/ .v-input__control > .v-input__slot:before,
.fix-border /deep/ .v-input__control > .v-input__slot:after {
    bottom: 0;
}
</style>
