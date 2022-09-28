<script>
import { mapActions } from "pinia";
import { useAlertsStore } from "../../stores/alerts_store";
import { useKidsStore } from "../../stores/kids_store";
import Modal from "../modal/Modal.vue";

export default {
  components: { Modal },

  props: {
    kid: Object,
  },

  computed: {
    kidDeletionModalText() {
      if (this.kid) {
        return (
          "Czy na pewno chcesz trwale usunąć informacje o dziecku " +
          this.kid.firstName +
          " " +
          this.kid.lastName +
          "?"
        );
      }
    },
  },

  methods: {
    ...mapActions(useAlertsStore, ["showAlert"]),
    ...mapActions(useKidsStore, ["delete"]),

    deleteKid() {
      this.delete(this.kid.id);
      this.showAlert(
        "Pomyślnie usunięto dziecko " +
          this.kid.firstName +
          " " +
          this.kid.lastName +
          " z listy",
        "success"
      );
      this.$emit("kidDeleted");
      this.$router.push({ path: "/dzieci" });
    },
  },

  emits: ["kidDeleted"]
};
</script>

<template>
  <Modal
    id="deleteConfirmationModal"
    title="Wymagane potwierdzenie"
    :text="kidDeletionModalText"
    actionName="Usuń"
    @confirmed="deleteKid"
  />
</template>