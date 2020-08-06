import Axios from "axios";
import decode from "jwt-decode";
import { BehaviorSubject } from "rxjs";

const STORAGE_KEYS_PREFIX = "03f9edab-0e5d-4120-8931-4d547b8595e3--";
const AUTH_TOKEN_STORAGE_KEY = STORAGE_KEYS_PREFIX + "auth-token";
const AUTH_USER_DATA_STORAGE_KEY = STORAGE_KEYS_PREFIX + "auth-user-data";


const userDataSubject = new BehaviorSubject(userData());


function login(data) {
    return new Promise((resolve, reject) => {
        Axios.post("auth/login", data).then(response => {
            Axios.defaults.headers.common.Authorization = `Bearer ${response.data.token}`;
            localStorage.setItem(AUTH_TOKEN_STORAGE_KEY, response.data.token);

            const userData = {
                email: response.data.email,
                username: response.data.username,
                roles: response.data.roles
            };
            localStorage.setItem(AUTH_USER_DATA_STORAGE_KEY, JSON.stringify(userData));

            userDataSubject.next(userData);
            resolve();
        }).catch(error => {
            reject(error.response);
        });
    });
}

function logout() {
    localStorage.removeItem(AUTH_TOKEN_STORAGE_KEY);
    localStorage.removeItem(AUTH_USER_DATA_STORAGE_KEY);
    userDataSubject.next(null);
}

function userAuthenticated() {
    const token = localStorage.getItem(AUTH_TOKEN_STORAGE_KEY);
    if (token && !tokenExpired(token)) {
        return true;
    }
    return false;
}

function userData() {
    if (!userAuthenticated()) {
        return null;
    }
    const currentUserData = JSON.parse(localStorage.getItem(AUTH_USER_DATA_STORAGE_KEY));
    return currentUserData || null;
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

function tokenExpired(token) {
    const expirationDate = getAuthTokenExpirationDate(token);
    return expirationDate < new Date();
}


export const AuthService = {
    login,
    logout,
    userAuthenticated,
    userData,
    userDataObservable: userDataSubject.asObservable()
};
