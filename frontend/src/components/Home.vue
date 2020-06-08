<template>
    <h4 class="text-center">Welcome to EPG application</h4>
</template>

<script>
import TestService from "@/services/test-service";

export default {
    name: "Home",
    created() {
        TestService.getTestData()
            .then(response => {
                console.log(response);
            })
            .catch(error => {
                console.error(error);
            });
    }
};
</script>
