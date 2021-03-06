import { AppConstants } from "@/models/constants/app-constants";
import Axios from "axios";
import decode from "jwt-decode";
import { BehaviorSubject } from "rxjs";


const userDataSubject = new BehaviorSubject(userData());

function login(data) {
    return new Promise((resolve, reject) => {
        Axios.post("auth/login", data).then(response => {
            Axios.defaults.headers.common.Authorization = `Bearer ${response.data.token}`;
            localStorage.setItem(AppConstants.AUTH_TOKEN_STORAGE_KEY, response.data.token);

            const userData = {
                email: response.data.email,
                username: response.data.username,
                roles: response.data.roles
            };
            localStorage.setItem(AppConstants.AUTH_USER_DATA_STORAGE_KEY, JSON.stringify(userData));

            userDataSubject.next(userData);
            resolve();
        }).catch(error => {
            reject(error.response ? error.response : error);
        });
    });
}

function logout() {
    localStorage.removeItem(AppConstants.AUTH_TOKEN_STORAGE_KEY);
    localStorage.removeItem(AppConstants.AUTH_USER_DATA_STORAGE_KEY);
    userDataSubject.next(null);
}

function nextUserData() {
    userDataSubject.next(userData());
}

function userHasToken() {
    const token = localStorage.getItem(AppConstants.AUTH_TOKEN_STORAGE_KEY);
    return !!token;
}

function userTokenExpired() {
    if (!userHasToken()) {
        return true;
    }
    const token = localStorage.getItem(AppConstants.AUTH_TOKEN_STORAGE_KEY);
    const expirationDate = getAuthTokenExpirationDate(token);
    return expirationDate < new Date();
}

function userData() {
    if (!userHasToken() || userTokenExpired()) {
        return null;
    }
    const currentUserData = JSON.parse(localStorage.getItem(AppConstants.AUTH_USER_DATA_STORAGE_KEY));
    return currentUserData || null;
}

function userHasAnyRole(...roles) {
    const userData = this.userData();
    if (!userData || !userData.roles || !userData.roles.length) {
        return false;
    }
    return userData.roles.some(r => roles.includes(r));
}

function getAuthTokenExpirationDate(token) {
    const tokenPayload = decode(token);
    if (!tokenPayload.exp) {
        return null;
    }

    const date = new Date(0);
    date.setUTCSeconds(tokenPayload.exp);
    return date;
}

export const AuthService = {
    login,
    logout,
    nextUserData,
    userHasToken,
    userTokenExpired,
    userData,
    userHasAnyRole,
    userDataObservable: userDataSubject.asObservable()
};
