<script>
import PageTitle from "../page/PageTitle.vue";
import PageContent from "../page/PageContent.vue";
import CarCard from "./CarCard.vue";
import { useCarsStore } from '../../stores/cars_store';
import { mapActions } from 'pinia';
import DeleteCarModal from "./DeleteCarModal.vue";

export default {
  components: { PageTitle, PageContent, CarCard, DeleteCarModal },

  data() {
    return {
      cars: [],
      carForDeletion: null,
    }
  },

  methods: {
    ...mapActions(useCarsStore, {
      getAllCars: "getAll"
    }),

    deleteCarRequested(carForDeletion) {
      this.carForDeletion = carForDeletion;
    },

    loadCars() {
      this.cars = this.getAllCars();
    },

    carDeleted() {
      this.loadCars();
      this.carForDeletion = null;
    }
  },

  mounted() {
    this.loadCars();
  }
};
</script>

<template>
  <PageTitle title="Samochody" />
  <PageContent>
    <div class="row mb-5">
      <div class="col">
        <router-link to="/samochody/dodaj" class="btn btn-success">Dodaj</router-link>
      </div>
    </div>
    <div class="row mb3">
      <div class="col-auto" v-for="car in cars" :key="car.id">
        <CarCard :car="car" @delete-car-requested="deleteCarRequested"/>
      </div>
    </div>
  </PageContent>
  <DeleteCarModal :car="carForDeletion" @car-deleted="carDeleted" />
</template>