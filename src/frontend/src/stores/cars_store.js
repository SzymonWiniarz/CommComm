import { defineStore } from "pinia";

export const useCarsStore = defineStore("cars", {

    state: () => {
        return {
            cars: [],
            carId: 1
        }
    },

    actions: {
        create(car) {
            const nextId = (this.carId++).toString();
            car.id = nextId;
            this.cars.push(car);
        },

        delete(id) {
            this.cars = this.cars.filter((car) => car.id != id);
        },

        getById(id) {
            const foundCar = this.cars.find((car) => car.id == id);

            if (foundCar) {
                return Object.assign({}, foundCar);
            }
        },

        getAll() {
            return this.cars.map((car) => Object.assign({}, car));
        },

        update(car) {
            this.delete(car.id);
            this.cars.push(car);
        }
    }

})