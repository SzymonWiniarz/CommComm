<script>
import Form from "../Form.vue";
import { mapActions } from "pinia";
import { useCarsStore } from "../../stores/cars_store";

export default {
  components: { Form },

  props: {
    carParam: Object,
    action: String,
  },

  data() {
    return {
      car: this.carParam,
    };
  },

  methods: {
    ...mapActions(useCarsStore, {
      createCarAction: "create",
      updateCarAction: "update",
    }),

    saveCarForm() {
      if (this.action == "create") {
        this.createCarAction(this.car);
      } else {
        this.updateCarAction(this.car);
      }

      this.$emit("submitted");
    },
  },

  watch: {
    carParam(newCarParam) {
      this.car = newCarParam;
    },
  },

  emits: ["submitted"],
};
</script>

<template>
  <div class="row justify-content-center">
    <Form id="carForm" :onSubmit="saveCarForm">
      <div class="row mb-3">
        <div class="col">
          <label for="carName" class="form-label">Nazwa</label>
          <input
            id="carName"
            class="form-control"
            v-model="car.name"
            required
          />
          <div class="invalid-feedback">Podaj nazwę samochodu</div>
        </div>
      </div>
      <div class="row mb-3">
        <div class="col">
          <label for="numberOfSeats" class="form-label">Liczba miejsc</label>
          <input
            type="number"
            id="numberOfSeats"
            class="form-control"
            v-model="car.numberOfSeats"
            required
          />
          <div id="numberOfSeatsHelp" class="form-text">
            Liczba miejsc w samochodzie dostępnych dla pasażerów
          </div>
          <div class="invalid-feedback">
            Podaj poprawną liczbę miejsc w samochodzie
          </div>
        </div>
        <div class="col">
          <label for="numberOfBabySeats" class="form-label"
            >Liczba fotelików dziecięcych</label
          >
          <input
            type="number"
            id="numberOfBabySeats"
            class="form-control"
            v-model="car.numberOfBabySeats"
            required
          />
          <div id="numberOfBabySeatsHelp" class="form-text">
            Liczba fotelików dziecięcych zamontowanch w samochodzie
          </div>
          <div class="invalid-feedback">
            Podaj poprawną liczbę fotelików dziecięcych
          </div>
        </div>
      </div>
      <button type="submit" class="btn btn-primary mb-3">Zapisz</button>
      <slot></slot>
    </Form>
  </div>
</template>

<style scoped>
#carForm {
  width: 80%;
}
</style>