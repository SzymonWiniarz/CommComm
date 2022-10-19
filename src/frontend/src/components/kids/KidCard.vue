<script>
import EditIcon from "../icons/EditIcon.vue";
import DeleteIcon from "../icons/DeleteIcon.vue";
import CalendarIcon from "../icons/CalendarIcon.vue";
import ModalTriggerButton from "../modal/ModalTriggerButton.vue";
import AbsenceIcon from "../icons/AbsenceIcon.vue";

export default {
  components: {
    EditIcon,
    DeleteIcon,
    ModalTriggerButton,
    CalendarIcon,
    AbsenceIcon,
  },

  props: {
    kid: Object,
  },

  methods: {
    getSexText() {
      if (this.kid.sex === "GIRL") {
        return "Dziewczynka";
      }
      if (this.kid.sex === "BOY") {
        return "Chłopiec";
      }
      return "";
    },

    openEditKidView() {
      const kidId = this.kid.id;
      this.$router.push({ path: "/dzieci/" + kidId });
    },

    kidDeletionRequested() {
      this.$emit("deleteKidRequested", this.kid);
    },
  },

  emits: ["deleteKidRequested"],
};
</script>

<template>
  <div class="card cc-kid-card">
    <div class="card-body">
      <h5 class="card-title mb-3" @click="openEditKidView">
        {{ kid.firstName + " " + kid.lastName }}
      </h5>
      <p class="card-text mb-3">
        <span class="badge" :class="kid.sex.toLowerCase()">{{
          this.getSexText()
        }}</span>
        <span v-if="kid.doesNeedBabySeat" class="badge bg-secondary"
          >Fotelik</span
        >
      </p>
      <div>
        <router-link
          class="btn btn-primary cc-quick-action-button"
          :to="'/dzieci/' + kid.id"
          ><EditIcon
        /></router-link>
        <router-link
          class="btn btn-secondary cc-quick-action-button"
          :to="'/dzieci/' + kid.id + '/plan'"
          ><CalendarIcon
        /></router-link>
        <router-link
          class="btn btn-secondary cc-quick-action-button"
          :to="'/dzieci/' + kid.id + '/nieobecnosci'"
          ><AbsenceIcon
        /></router-link>
        <ModalTriggerButton
          buttonClass="btn-danger cc-quick-action-button"
          modalId="deleteConfirmationModal"
          @click="kidDeletionRequested"
          ><DeleteIcon
        /></ModalTriggerButton>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.card-title {
  cursor: pointer;
}

.badge.girl {
  background-color: $color-pink;
}

.badge.boy {
  background-color: $color-cyan;
}

.cc-quick-action-button {
  margin: 1px;
}
</style>