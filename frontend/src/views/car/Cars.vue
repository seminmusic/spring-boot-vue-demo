<template>
    <div>
        <b-row class="my-3">
            <b-col md="2" offset-md="10">
                <router-link :to="{ name: 'CarCreate' }">Add new car</router-link>
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
                            <car-list-row v-for="c in cars" :key="c.id" :car="c"
                                @details="carDetails"
                                @edit="carEdit"
                                @delete="carDelete" />
                        </tbody>
                    </table>
                </div>
            </b-col>
        </b-row>

        <b-modal
            ref="deleteConfirmModal"
            title="Confirm delete"
            @ok="onDeleteConfirm"
            @hidden="onConfirmModalHidden">
            <p>Are you sure you want to delete this car?</p>
            <strong>{{ selectedCar.manufacturer + ' ' + selectedCar.model }}</strong>
        </b-modal>

        <b-modal
            ref="alertModal"
            :title="alertModalTitle"
            :ok-only="true">
            <p>{{ alertModalContent }}</p>
        </b-modal>
    </div>
</template>

<script>
import CarListRow from "@/views/car/components/CarListRow.component";
import CarService from "@/services/car.service";

export default {
    name: "Cars",
    components: {
        CarListRow
    },
    data() {
        return {
            cars: [],
            selectedCar: {},
            alertModalTitle: "",
            alertModalContent: ""
        };
    },
    created() {
        this.getCars();
    },
    methods: {
        getCars() {
            CarService.getCars().then(response => {
                this.cars = response.data;
            });
        },
        carDetails(carId) {
            this.$router.push({ name: "CarDetails", params: { id: carId } });
        },
        carEdit(carId) {
            this.$router.push({ name: "CarUpdate", params: { id: carId } });
        },
        carDelete(carId) {
            this.selectedCar = this.cars.find(c => c.id === carId);
            this.$refs.deleteConfirmModal.show();
        },
        onDeleteConfirm() {
            CarService.deleteCar(this.selectedCar.id).then(response => {
                this.alertModalTitle = "Delete successful";
                this.alertModalContent = "Successfully deleted " + this.selectedCar.manufacturer + " " + this.selectedCar.model;
                this.$refs.alertModal.show();
                this.getCars();
            }).catch(error => {
                this.alertModalTitle = "Error deleting car";
                this.alertModalContent = error.response.data.message;
                this.$refs.alertModal.show();
            });
        },
        onConfirmModalHidden() {
            this.selectedCar = {};
        }
    }
};
</script>
