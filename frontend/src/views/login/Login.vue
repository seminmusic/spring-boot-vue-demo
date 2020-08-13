<template>
    <v-row align="center" justify="center">
        <v-col cols="12" sm="8" md="4">
            <v-card class="elevation-10">
                <v-form ref="loginForm" @submit.prevent="onLoginSubmit" autocomplete="off">
                    <v-toolbar color="primary" dark flat dense rounded="t">
                        <v-spacer />
                        <v-toolbar-title>Login</v-toolbar-title>
                        <v-spacer />
                    </v-toolbar>
                    <v-card-text>
                        <v-alert type="warning" icon="mdi-alert-circle" class="alert-icon-center text-caption" dense outlined v-if="infoMessage">{{ infoMessage }}</v-alert>
                        <v-text-field type="text" class="fix-border" label="Username" prepend-icon="mdi-account"
                            v-model="loginData.username" :rules="loginData.usernameRules" />
                        <div style="padding:1px 0"></div>
                        <v-text-field :type="showPassword ? 'text' : 'password'" class="fix-border" label="Password" prepend-icon="mdi-lock" :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'" @click:append="showPassword = !showPassword"
                            v-model="loginData.password" :rules="loginData.passwordRules" />
                        <!-- <v-alert type="error" class="alert-icon-center text-caption mb-0" dense v-if="errorMessage">{{ errorMessage }}</v-alert> -->
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
            <v-snackbar app v-model="errorSnackbar" top color="error" class="error-snackbar" transition="slide-y-transition">{{ errorMessage }}</v-snackbar>
        </v-col>
    </v-row>
</template>


<script>
import { AuthService } from "@/services/auth-service";
import { UtilityHelper } from "@/helpers/utility-helper";

export default {
    name: "Login",
    data() {
        return {
            loginData: {
                username: "",
                usernameRules: [
                    v => !!v || "Username is required"
                ],
                password: "",
                passwordRules: [
                    v => !!v || "Password is required"
                ]
            },
            showPassword: false,
            infoMessage: "",
            errorSnackbar: false,
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
            if (!this.$refs.loginForm.validate()) {
                return;
            }

            this.loading = true;
            this.errorSnackbar = false;
            AuthService.login(this.loginData).then(response => {
                console.log("Redirecting after successful login to: " + this.returnUrl);
                this.$router.push(this.returnUrl).catch(() => {});
            }).catch(error => {
                console.error("Login error", error);
                this.errorMessage = (error && error.data && error.data.message) ? error.data.message : error;
                this.errorSnackbar = true;
            }).finally(() => {
                this.loading = false;
            });
        }
    }
};

// Private functions
function parseRouteData(query) {
    if (UtilityHelper.isEmptyObject(query)) {
        this.infoMessage = "";
        this.returnUrl = "/";
        return;
    }

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

/* .fix-border /deep/ .v-input__control > .v-input__slot:before,
.fix-border /deep/ .v-input__control > .v-input__slot:after {
    bottom: -2px;
} */

.alert-icon-center /deep/ .v-icon {
    align-self: center;
}
.error-snackbar /deep/ .v-snack__wrapper {
    margin-top: 24px !important;
}
</style>
