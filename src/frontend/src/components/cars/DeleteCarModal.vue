<script>
import { mapActions } from "pinia";
import { useAlertsStore } from "../../stores/alerts_store";
import { useCarsStore } from "../../stores/cars_store";
import Modal from "../modal/Modal.vue";

export default {
  components: { Modal },

  props: {
    car: Object,
  },

  computed: {
    deleteCarModalText() {
      if (this.car) {
        return (
          "Czy na pewno chcesz trwale usunąć informacje o samochodzie " +
          this.car.name +
          "?"
        );
      }
    },
  },

  methods: {
    ...mapActions(useAlertsStore, ["showAlert"]),
    ...mapActions(useCarsStore, ["delete"]),

    deleteCar() {
      this.delete(this.car.id);
      this.showAlert(
        "Pomyślnie usunięto samochód " + this.car.name + " z listy",
        "success"
      );
      
      this.$emit("carDeleted");
      this.$router.push({ path: "/samochody" });
    },
  },

  emits: ["carDeleted"]
};
</script>

<template>
  <Modal
    id="deleteConfirmationModal"
    title="Wymagane potwierdzenie"
    :text="deleteCarModalText"
    actionName="Usuń"
    @confirmed="deleteCar"
  />
</template>