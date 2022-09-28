<script>
import EditIcon from "../icons/EditIcon.vue";
import DeleteIcon from "../icons/DeleteIcon.vue";
import ModalTriggerButton from "../modal/ModalTriggerButton.vue";

export default {
    props: {
        car: Object,
    },
    methods: {
        openEditCarView() {
            const carId = this.car.id;
            this.$router.push({ path: "/samochody/" + carId });
        },
        carDeletionRequested() {
            this.$emit("deleteCarRequested", this.car);
        },
    },
    emits: ["deleteCarRequested"],
    components: { EditIcon, DeleteIcon, ModalTriggerButton }
};
</script>

<template>
  <div class="card cc-car-card">
    <div class="card-body">
      <h5 class="card-title mb-3" @click="openEditCarView">{{ car.name }}</h5>
      <p class="card-text">
        {{ car.numberOfSeats }} miejsc, {{ car.numberOfBabySeats }} fotelików
      </p>
      <div>
        <router-link
          class="btn btn-primary cc-quick-action-button"
          :to="'/samochody/' + car.id"
          ><EditIcon
        /></router-link>
        <ModalTriggerButton
          buttonClass="btn-danger cc-quick-action-button"
          modalId="deleteConfirmationModal"
          @click="carDeletionRequested"
          ><DeleteIcon
        /></ModalTriggerButton>
      </div>
    </div>
  </div>
</template>

<style scoped>
  
.card-title {
  cursor: pointer;
}

.cc-quick-action-button {
  margin: 1px;
}

</style>