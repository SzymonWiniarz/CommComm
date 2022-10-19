<script>
import Form from "../Form.vue";
import PageTitle from "../page/PageTitle.vue";
import PageContent from "../page/PageContent.vue";
import { mapActions } from "pinia";
import { useCarsStore } from "../../stores/cars_store";
import CarSeatCard from "./CarSeatCard.vue";
import { useLessonsStore } from "../../stores/lessons_store";
import LessonPicker from "../lessons/LessonPicker.vue";
import { todayAsString } from "../../utils.js";
import { useKidsStore } from "../../stores/kids_store";

export default {
  components: { Form, PageTitle, PageContent, CarSeatCard, LessonPicker },

  data() {
    return {
      allCars: [],
      allLessons: [],
      availableKids: [],

      transport: null,
    };
  },

  computed: {
    selectedCar() {
      return this.allCars.find((car) => car.id == this.transport.carId);
    },

    selectedKidIds() {
      return this.transport.seats
        .map((seat) => seat.kidId)
        .filter((kidId) => kidId != null);
    },
  },

  watch: {
    selectedCar(newCar) {
      for (let seatId = 1; seatId <= newCar.numberOfSeats; seatId++) {
        this.transport.seats.push({
          id: seatId,
          hasBabySeat: this.hasBabySeat(seatId),
          isReserved: false,
          kidId: null,
        });
      }
    },
  },

  methods: {
    ...mapActions(useCarsStore, {
      getAllCarsAction: "getAll",
    }),

    ...mapActions(useLessonsStore, ["getAllLessons"]),
    ...mapActions(useKidsStore, {
      getAvailableKids: "getAllForCommuting",
    }),

    todayAsString() {
      return todayAsString();
    },

    saveTransportForm() {
      alert("Zapisano transport: " + JSON.stringify(this.transport));
    },

    hasBabySeat(seatId) {
      return seatId <= this.selectedCar.numberOfBabySeats;
    },

    stillAvailableKids(selectedKidId) {
      return this.availableKids.filter(kid => kid.id == selectedKidId || !this.selectedKidIds.includes(kid.id));
    }
  },

  created() {
    this.allCars = this.getAllCarsAction();
    this.allLessons = this.getAllLessons();
    this.availableKids = this.getAvailableKids();

    this.transport = {
      day: null,
      lessonId: null,
      carId: null,
      seats: [],
    };
  },
};
</script>

<template>
  <PageTitle title="Zadeklaruj dowóz" />
  <PageContent>
    <div class="row justify-content-center">
      <Form id="transportForm" :onSubmit="saveTransportForm">
        <div class="row mb-3">
          <div class="col">
            <label for="dateSelect" class="form-label">Kiedy</label>
            <input
              id="dateSelect"
              type="date"
              :min="todayAsString()"
              class="form-control"
              v-model="transport.day"
              required
            />
          </div>
          <div class="col">
            <label for="lessonSelect" class="form-label"
              >Godzina rozpoczęcia lekcji</label
            >
            <LessonPicker
              id="lessonSelect"
              type="START"
              :lessons="allLessons"
              v-model="transport.lessonId"
              required
            />
          </div>
        </div>
        <div class="row mb-3">
          <div class="col-4">
            <label for="carSelect" class="form-label">Samochód</label>
            <select
              id="carSelect"
              class="form-select"
              aria-label="Samochód"
              v-model="transport.carId"
              required
            >
              <option disabled selected value="null">Wybierz samochód</option>
              <option v-for="car in allCars" :value="car.id" :key="car.id">
                {{ car.name }}
              </option>
            </select>
          </div>
        </div>
        <div class="row mb-3" v-if="selectedCar">
          <p>Miejsca</p>
          <div class="col" v-for="(carSeat, index) in transport.seats" :key="carSeat.id">
            <CarSeatCard
              :availableKids="stillAvailableKids(carSeat.kidId)"
              v-model="transport.seats[index]"
            />
          </div>
        </div>
        <button type="submit" class="btn btn-primary">Zapisz</button>
      </Form>
    </div>
  </PageContent>
</template>

<style scoped>
#transportForm {
  width: 80%;
}
</style>