<template>
    <b-container fluid>
        <div class="form-wrapper">
            <b-form @submit.prevent="updateCar">
                <b-form-group label-cols="2" breakpoint="md" horizontal label="Manufacturer:" for="manufacturer">
                    <b-col md="5">
                        <b-input id="manufacturer" v-model="formData.manufacturer" maxlength="60" required />
                    </b-col>
                </b-form-group>
                <b-form-group label-cols="2" breakpoint="md" horizontal label="Model:" for="model">
                    <b-col md="5">
                        <b-input id="model" v-model="formData.model" maxlength="60" required />
                    </b-col>
                </b-form-group>
                <b-form-group label-cols="2" breakpoint="md" horizontal label="Release year:" for="releaseYear">
                    <b-col md="5">
                        <b-input id="releaseYear" v-model="formData.releaseYear" type="number" required />
                    </b-col>
                </b-form-group>
                <b-form-group label-cols="2" breakpoint="md" horizontal label="Last change:" for="lastChange">
                    <b-col md="5">
                        <b-input id="lastChange" v-model="formData.lastChange" type="datetime-local" required />
                    </b-col>
                </b-form-group>
                <b-col md="5" offset="4">
                    <b-button type="submit" variant="info">Update</b-button>
                    <b-button :to="{ name: 'Cars' }" variant="danger">Cancel</b-button>
                </b-col>
            </b-form>
        </div>

        <b-modal
            ref="alertModal"
            :title="alertModalTitle"
            :ok-only="true"
            @ok="onAlertModalOkClick">
            <p>{{ alertModalContent }}</p>
        </b-modal>
    </b-container>
</template>


<script>
import CarService from "@/services/car-service";

export default {
    name: "CarUpdate",
    data() {
        return {
            formData: {
                manufacturer: "",
                model: "",
                releaseYear: "",
                lastChange: ""
            },
            alertModalTitle: "",
            alertModalContent: "",
            isSuccessfully: false
        };
    },
    created() {
        CarService.getCar(this.$router.currentRoute.params.id).then(response => {
            this.formData.manufacturer = response.data.manufacturer;
            this.formData.model = response.data.model;
            this.formData.releaseYear = response.data.releaseYear;
            this.formData.lastChange = response.data.lastChange;
        });
    },
    methods: {
        updateCar() {
            CarService.updateCar(this.$router.currentRoute.params.id, this.formData).then(response => {
                this.isSuccessfully = true;
                this.alertModalTitle = "Success";
                this.alertModalContent = "Successfully updated car";
                this.$refs.alertModal.show();
            }).catch(error => {
                this.isSuccessfully = false;
                this.alertModalTitle = "Error";
                this.alertModalContent = error.response.data;
                this.$refs.alertModal.show();
            });
        },
        onAlertModalOkClick() {
            if (this.isSuccessfully) {
                this.$router.push({ name: "Cars" });
            }
        }
    }
};
</script>


<style>
.form-wrapper {
    margin-top: 20px;
    min-height: 20px;
    padding: 19px;
    margin-bottom: 20px;
    background-color: #f5f5f5;
    border: 1px solid #e3e3e3;
    border-radius: 4px;
    box-shadow: inset 0 1px 1px rgba(0,0,0,.05);
}
</style>
