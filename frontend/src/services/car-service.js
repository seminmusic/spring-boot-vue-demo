import Axios from "axios";


export default {
    getCars() {
        return Axios.get("car");
    },
    getCar(id) {
        return Axios.get(`car/${id}`);
    },
    deleteCar(id) {
        return Axios.delete(`car/${id}`);
    },
    createCar(carData) {
        return Axios.post("car", carData);
    },
    updateCar(id, carData) {
        return Axios.put(`car/${id}`, carData);
    }
};
