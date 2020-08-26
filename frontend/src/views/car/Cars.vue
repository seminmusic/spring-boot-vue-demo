<template>
    <div>
        <v-data-table
            :headers="tableHeaders"
            :items="cars"
            sort-by="lastChange" sort-desc
            class="elevation-3"
            :hide-default-footer="!cars.length">
            <template v-slot:top>
                <v-toolbar flat>
                    <v-toolbar-title>Car List</v-toolbar-title>
                    <v-divider class="mx-4" inset vertical></v-divider>
                    <v-spacer></v-spacer>
                    <v-dialog v-model="showChangeDialog" persistent max-width="600px">
                        <template v-slot:activator="{ on, attrs }">
                            <v-btn color="primary" dark v-if="userHasAnyRole(Role.ADD)" v-bind="attrs" v-on="on">Add new car</v-btn>
                        </template>
                        <v-card>
                            <v-form ref="carForm">
                                <v-card-title class="px-6">{{ isEdit ? 'Edit car' : 'New car' }}</v-card-title>
                                <v-divider></v-divider>
                                <v-card-text class="px-6 pb-3">
                                    <v-container class="pa-0">
                                        <v-row>
                                            <v-col cols="12" sm="6">
                                                <v-text-field v-model="editedCar.manufacturer" label="Manufacturer" :rules="editedCarRules.manufacturer"></v-text-field>
                                            </v-col>
                                            <v-col cols="12" sm="6">
                                                <v-text-field v-model="editedCar.model" label="Model" :rules="editedCarRules.model"></v-text-field>
                                            </v-col>
                                        </v-row>
                                        <v-row>
                                            <v-col cols="12" sm="6">
                                                <v-text-field v-model="editedCar.releaseYear" label="Release year" type="number" :rules="editedCarRules.releaseYear"></v-text-field>
                                            </v-col>
                                            <v-col cols="12" sm="6">
                                                <v-text-field v-model="editedCar.lastChange" label="Last changed" type="datetime-local" :rules="editedCarRules.lastChange"></v-text-field>
                                            </v-col>
                                        </v-row>
                                    </v-container>
                                </v-card-text>
                                <v-divider></v-divider>
                                <v-card-actions class="px-6 py-3">
                                    <v-spacer></v-spacer>
                                    <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
                                    <v-btn color="primary" @click="save">{{ isEdit ? 'Save changes' : 'Save new car' }}</v-btn>
                                </v-card-actions>
                            </v-form>
                        </v-card>
                    </v-dialog>
                </v-toolbar>
            </template>
            <template v-slot:[`item.actions`]="{ item }">
                <v-icon small :class="{'mr-2': iconCount > 1}" @click="showCarInfo(item)" v-if="userHasAnyRole(Role.VIEW)">mdi-car-info</v-icon>
                <v-icon small :class="{'mr-2': iconCount > 2}" @click="editCar(item)" v-if="userHasAnyRole(Role.EDIT)">mdi-pencil</v-icon>
                <v-icon small @click="deleteCar(item)" v-if="userHasAnyRole(Role.DELETE)">mdi-delete</v-icon>
            </template>
            <template v-slot:no-data>
                No cars available
            </template>
        </v-data-table>
        <v-snackbar app v-model="errorSnackbar" top color="error" class="error-snackbar" transition="slide-y-transition">{{ errorMessage }}</v-snackbar>
    </div>
</template>


<script>
import { AuthService } from "@/services/auth-service";
import { Role } from "@/models/constants/role-constants";
import CarService from "@/services/car-service";

export default {
    name: "Cars",
    data() {
        return {
            Role: Role,
            carsTable: {
                headers: [
                    { text: "ID", value: "id" },
                    { text: "Manufacturer", value: "manufacturer" },
                    { text: "Model", value: "model" },
                    { text: "Release year", value: "releaseYear" },
                    { text: "Last changed", value: "lastChange" },
                    { text: "Actions", value: "actions", align: "center", sortable: false }
                ]
            },
            cars: [],
            showChangeDialog: false,
            editedCar: {
                id: undefined,
                manufacturer: undefined,
                model: undefined,
                releaseYear: undefined,
                lastChange: undefined
            },
            editedCarRules: {
                manufacturer: [
                    v => !!v || "Manufacturer is required"
                ],
                model: [
                    v => !!v || "Model is required"
                ],
                releaseYear: [
                    v => !!v || "Release year is required"
                ],
                lastChange: [
                    v => !!v || "Last change is required"
                ]
            },
            errorSnackbar: false,
            errorMessage: ""
        };
    },
    created() {
        this.getCars();
    },
    computed: {
        tableHeaders() {
            return this.userHasAnyRole(this.Role.VIEW, this.Role.EDIT, this.Role.DELETE) ? this.carsTable.headers : this.carsTable.headers.filter(h => h.value !== "actions");
        },
        iconCount() {
            let iconCount = 0;
            if (this.userHasAnyRole(this.Role.VIEW)) iconCount++;
            if (this.userHasAnyRole(this.Role.EDIT)) iconCount++;
            if (this.userHasAnyRole(this.Role.DELETE)) iconCount++;
            return iconCount;
        },
        isEdit() {
            return !!this.editedCar.id;
        }
    },
    methods: {
        userHasAnyRole(...roles) {
            return AuthService.userHasAnyRole(...roles);
        },
        getCars() {
            CarService.getCars().then(response => {
                this.cars = response.data;
            }).catch(error => {
                this.errorMessage = (error && error.data && error.data.message) ? error.data.message : error;
                this.errorSnackbar = true;
            });
        },
        showCarInfo(car) {
            this.$router.push({ name: "CarDetails", params: { id: car.id } }).catch(() => {});
        },
        editCar(car) {
            CarService.getCar(car.id).then(response => {
                this.editedCar = response.data;
                this.showChangeDialog = true;
            }).catch(error => {
                this.errorMessage = (error && error.data && error.data.message) ? error.data.message : error;
                this.errorSnackbar = true;
            });
        },
        deleteCar(car) {
            if (confirm("Are you sure you want to delete this car?")) {
                CarService.deleteCar(car.id).then(response => {
                    this.getCars();
                }).catch(error => {
                    this.errorMessage = (error && error.data && error.data.message) ? error.data.message : error;
                    this.errorSnackbar = true;
                });
            }
        },
        close() {
            this.showChangeDialog = false;
            this.editedCar = {
                id: undefined,
                manufacturer: undefined,
                model: undefined,
                releaseYear: undefined,
                lastChange: undefined
            };
            this.$refs.carForm.reset();
        },
        save() {
            if (!this.$refs.carForm.validate()) {
                return;
            }
            if (this.isEdit) {
                CarService.updateCar(this.editedCar.id, this.editedCar).then(response => {
                    this.close();
                    this.getCars();
                }).catch(error => {
                    this.errorMessage = (error && error.data && error.data.message) ? error.data.message : error;
                    this.errorSnackbar = true;
                });
            } else {
                CarService.createCar(this.editedCar).then(response => {
                    this.close();
                    this.getCars();
                }).catch(error => {
                    this.errorMessage = (error && error.data && error.data.message) ? error.data.message : error;
                    this.errorSnackbar = true;
                });
            }
        }
    }
};
</script>


<style scoped>
.error-snackbar /deep/ .v-snack__wrapper {
    margin-top: 24px !important;
}
</style>
