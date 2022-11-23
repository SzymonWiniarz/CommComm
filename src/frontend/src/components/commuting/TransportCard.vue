<script>
import EditIcon from "../icons/EditIcon.vue";
import DeleteIcon from "../icons/DeleteIcon.vue";
import ModalTriggerButton from "../modal/ModalTriggerButton.vue";
import { mapActions } from "pinia";
import { useLessonsStore } from "../../stores/lessons_store";
import { dayFriendlyLabel } from "../../utils.js";

export default {
  components: { EditIcon, DeleteIcon, ModalTriggerButton },

  props: {
    transport: Object,
  },

  computed: {
    cardTitle() {
      const day = this.transport.day;
      const dayLabel = dayFriendlyLabel(new Date(day));
      const lesson = this.getAllLessons().find(
        (lesson) => lesson.id == this.transport.lessonId
      );
      const time =
        this.transport.direction == "TO" ? lesson.startsAt : lesson.endsAt;

      return dayLabel + " godz. " + time;
    },

    editUrl() {
      return this.transport.direction == "TO"
        ? "/dowozy/" + this.transport.id
        : "/odbiory/" + this.transport.id;
    },
  },

  methods: {
    ...mapActions(useLessonsStore, ["getAllLessons"]),

    openEditTransportView() {
      this.$router.push({ path: this.editUrl });
    },

    transportDeletionRequested() {
      this.$emit("deleteTransportRequested", this.transport);
    },
  },

  emits: ["deleteTransportRequested"],
};
</script>

<template>
  <div class="card cc-transport-card">
    <div class="card-body">
      <h5 class="card-title mb-3" @click="openEditTransportView">
        {{ cardTitle }}
      </h5>
      <p class="card-text"></p>
      <div>
        <router-link
          class="btn btn-primary cc-quick-action-button"
          :to="editUrl"
          ><EditIcon
        /></router-link>
        <ModalTriggerButton
          buttonClass="btn-danger cc-quick-action-button"
          modalId="deleteConfirmationModal"
          @click="transportDeletionRequested"
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