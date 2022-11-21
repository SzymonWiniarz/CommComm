<script>
import { mapActions } from "pinia";
import { useAlertsStore } from "../../stores/alerts_store";
import { useCarsStore } from "../../stores/cars_store";
import { useTransportsStore } from "../../stores/transports_store";
import Modal from "../modal/Modal.vue";

export default {
  components: { Modal },

  props: {
    transport: Object,
  },

  computed: {
    deleteTransportModalText() {
      if (this.transport) {
        const directionText =
          this.transport.direction == "TO" ? "dowóz" : "odbiór";

        return (
          "Czy na pewno chcesz anulować " +
          directionText +
          " dnia " +
          this.transport.day +
          "?"
        );
      }
    },
  },

  methods: {
    ...mapActions(useAlertsStore, ["showAlert"]),
    ...mapActions(useTransportsStore, [
      "deleteTransportToSchool",
      "deleteTransportFromSchool",
    ]),

    deleteTransport() {
      const deleteMethod =
        this.transport.direction == "TO"
          ? this.deleteTransportToSchool
          : this.deleteTransportFromSchool;

      const directionText =
        this.transport.direction == "TO" ? "dowóz" : "odbiór";

      deleteMethod(this.transport.id);
      this.showAlert(
        "Pomyślnie anulowano " + directionText + " dnia " + this.transport.day,
        "success"
      );

      this.$emit("transportDeleted");
      this.$router.push({ path: "/przewozy" });
    },
  },

  emits: ["transportDeleted"],
};
</script>

<template>
  <Modal
    id="deleteConfirmationModal"
    title="Wymagane potwierdzenie"
    :text="deleteTransportModalText"
    actionName="Usuń"
    @confirmed="deleteTransport"
  />
</template>