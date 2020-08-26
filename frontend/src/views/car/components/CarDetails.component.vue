<template>
    <div>
        <strong>Manufacturer:</strong> {{ car.manufacturer }}
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
