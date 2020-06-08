import Axios from "axios";

export default {
    getTestData() {
        return Axios.get("test-data");
    }
};
