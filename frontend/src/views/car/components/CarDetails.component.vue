<template>
    <div>
        <div class="well">
            <div class="row">
                <div class="col-md-3">
                    <strong>Manufacturer</strong>
                </div>
                <div class="col-md-3">
                    {{ car.manufacturer }}
                </div>
            </div>
        </div>
    </div>
</template>


<script>
import CarService from "@/services/car-service";

export default {
    name: "CarDetails",
    data() {
        return {
            car: {}
        };
    },
    created() {
        CarService.getCar(this.$router.currentRoute.params.id).then(response => {
            this.car = response.data;
        });
    }
};
</script>
