<template>
    <div>
        <b-row>
            <b-col md="2" offset-md="10">
                <a href="#">Add new car</a>
            </b-col>
        </b-row>
        <b-row>
            <b-col md="12">
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Manufacturer</th>
                                <th>Model</th>
                                <th>Release year</th>
                                <th>Last changed</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="car in cars" :key="car.id">
                                <td>{{ car.id }}</td>
                                <td>{{ car.manufacturer }}</td>
                                <td>{{ car.model }}</td>
                                <td>{{ car.releaseYear }}</td>
                                <td>{{ car.lastChange }}</td>
                                <td>
                                    <b-button variant="primary" size="sm">Details</b-button>
                                    <b-button variant="success" size="sm">Edit</b-button>
                                    <b-button variant="danger" size="sm">Delete</b-button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </b-col>
        </b-row>
    </div>
</template>

<script>
import CarService from "@/services/car.service";

export default {
    name: "CarList",
    data() {
        return {
            cars: []
        };
    },
    created() {
        CarService.getAll().then(response => {
            this.cars = response.data;
        });
    }
};
</script>

<style scoped>
td button:not(:last-child) {
    margin-right: 0.75rem;
}
</style>
