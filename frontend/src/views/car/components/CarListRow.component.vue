<template>
    <tr>
        <td>{{ car.id }}</td>
        <td>{{ car.manufacturer }}</td>
        <td>{{ car.model }}</td>
        <td>{{ car.releaseYear }}</td>
        <td>{{ car.lastChange }}</td>
        <td v-if="userHasAnyRole(Role.VIEW, Role.EDIT, Role.DELETE)">
            <b-button variant="primary" size="sm" @click="onDetailsClick" v-if="userHasAnyRole(Role.VIEW)">Details</b-button>
            <b-button variant="success" size="sm" @click="onEditClick" v-if="userHasAnyRole(Role.EDIT)">Edit</b-button>
            <b-button variant="danger" size="sm" @click="onDeleteClick" v-if="userHasAnyRole(Role.DELETE)">Delete</b-button>
        </td>
    </tr>
</template>


<script>
import { AuthService } from "@/services/auth-service";
import { Role } from "@/models/constants/role-constants";

export default {
    name: "CarListRow",
    props: ["car"],
    data() {
        return {
            Role: Role
        };
    },
    methods: {
        userHasAnyRole(...roles) {
            return AuthService.userHasAnyRole(...roles);
        },
        onDetailsClick() {
            this.$emit("details", this.car.id);
        },
        onEditClick() {
            this.$emit("edit", this.car.id);
        },
        onDeleteClick() {
            this.$emit("delete", this.car.id);
        }
    }
};
</script>


<style scoped>
td button:not(:last-child) {
    margin-right: 0.75rem;
}
</style>
