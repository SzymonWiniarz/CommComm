<script>
import PageTitle from "../page/PageTitle.vue";
import PageContent from "../page/PageContent.vue";
import CarCard from "./CarCard.vue";
import { useCarsStore } from '../../stores/cars_store';
import { mapActions } from 'pinia';

export default {
  components: { PageTitle, PageContent, CarCard },

  data() {
    return {
      cars: []
    }
  },

  methods: {
    ...mapActions(useCarsStore, {
      getAllCars: "getAll"
    })
  },

  mounted() {
    this.cars = this.getAllCars();
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
        <CarCard :car="car"/>
      </div>
    </div>
  </PageContent>
</template>