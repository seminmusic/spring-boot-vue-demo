<template>
    <tr>
        <td>{{ car.id }}</td>
        <td>{{ car.manufacturer }}</td>
        <td>{{ car.model }}</td>
        <td>{{ car.releaseYear }}</td>
        <td>{{ car.lastChange }}</td>
        <td>
            <b-button variant="primary" size="sm" @click="onDetailsClick">Details</b-button>
            <b-button variant="success" size="sm" @click="onEditClick">Edit</b-button>
            <b-button variant="danger" size="sm" @click="onDeleteClick">Delete</b-button>
        </td>
    </tr>
</template>

<script>
export default {
    name: "CarListRow",
    props: ["car"],
    methods: {
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
