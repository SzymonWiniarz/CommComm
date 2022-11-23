<script>
import Form from "../Form.vue";
import { mapActions } from "pinia";
import { useCarsStore } from "../../stores/cars_store";
import CarSeatCard from "./CarSeatCard.vue";
import { useLessonsStore } from "../../stores/lessons_store";
import LessonPicker from "../lessons/LessonPicker.vue";
import { todayAsString } from "../../utils.js";
import { useKidsStore } from "../../stores/kids_store";
import { useTransportsStore } from "../../stores/transports_store";
import { useAlertsStore } from "../../stores/alerts_store";

export default {
  components: { Form, CarSeatCard, LessonPicker },

  props: {
    action: String,
    direction: String,
    transportParam: Object,
  },

  data() {
    return {
      allCars: [],
      allLessons: [],
      availableKids: [],

      transport: this.transportParam,
    };
  },

  computed: {
    isEditScreen() {
      return this.action == "edit";
    },

    lessonPickerLabel() {
      return (
        "Godzina " +
        (this.direction == "TO" ? "rozpoczęcia" : "zakończenia") +
        " lekcji"
      );
    },

    lessonPickerType() {
      return this.direction == "TO" ? "START" : "END";
    },

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
      if (this.action == "create") {
        this.transport.seats.length = 0;
        for (let seatId = 1; seatId <= newCar.numberOfSeats; seatId++) {
          this.transport.seats.push({
            id: seatId,
            hasBabySeat: this.hasBabySeat(seatId),
            isReserved: false,
            kidId: null,
            note: null,
          });
        }
      }
    },

    transportParam(newTransportParam) {
      this.transport = newTransportParam;
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
    ...mapActions(useTransportsStore, [
      "createTransportToSchool",
      "createTransportFromSchool",
      "updateTransportToSchool",
      "updateTransportFromSchool"
    ]),

    todayAsString() {
      return todayAsString();
    },

    saveTransportForm() {
      if (this.action == "create") {
        if (this.direction == "TO") {
          this.createTransportToSchool(this.transport);
        } else {
          this.createTransportFromSchool(this.transport);
        }
      } else {
        if (this.direction == "TO") {
          this.updateTransportToSchool(this.transport);
        } else {
          this.updateTransportFromSchool(this.transport);
        }
      }

      this.$emit("submitted");
    },

    hasBabySeat(seatId) {
      return seatId <= this.selectedCar.numberOfBabySeats;
    },

    stillAvailableKids(selectedKidId) {
      return this.availableKids.filter(
        (kid) =>
          kid.id == selectedKidId || !this.selectedKidIds.includes(kid.id)
      );
    },
  },

  created() {
    this.allCars = this.getAllCarsAction();
    this.allLessons = this.getAllLessons();

    this.availableKids = this.getAvailableKids();
  },

  emits: ["submitted"],
};
</script>

<template>
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
            :disabled="isEditScreen"
          />
        </div>
        <div class="col">
          <label for="lessonSelect" class="form-label">{{
            lessonPickerLabel
          }}</label>
          <LessonPicker
            id="lessonSelect"
            :type="lessonPickerType"
            :lessons="allLessons"
            v-model="transport.lessonId"
            required
            :disabled="isEditScreen"
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
            :disabled="isEditScreen"
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
        <div
          class="col"
          v-for="(carSeat, index) in transport.seats"
          :key="carSeat.id"
        >
          <CarSeatCard
            :availableKids="stillAvailableKids(carSeat.kidId)"
            v-model="transport.seats[index]"
          />
        </div>
      </div>
      <div class="row mb-3">
        <div class="col">
          <label for="note" class="form-label">Wiadomość dla rodziców</label>
          <textarea
            class="form-control"
            id="note"
            rows="3"
            v-model="transport.note"
          ></textarea>
        </div>
      </div>
      <button type="submit" class="btn btn-primary">Zapisz</button>
    </Form>
  </div>
</template>

<style scoped>
#transportForm {
  width: 80%;
}
</style>