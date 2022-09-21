<script>
export default {
  props: {
    car: Object
  },

  methods: {
    openEditCarView() {
      const carId = this.car.id;
      this.$router.push({path: "/samochody/" + carId});
    },
  }
}
</script>

<template>
  <div class="card cc-car-card">
    <div class="card-body">
      <h5 class="card-title mb-3" @click="openEditCarView">{{ car.name }}</h5>
      <p class="card-text">
       {{ car.numberOfSeats }} miejsc, {{ car.numberOfBabySeats }} fotelików
      </p>
    </div>
  </div>
</template>