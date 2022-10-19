<script>
import PageTitle from "../page/PageTitle.vue";
import PageContent from "../page/PageContent.vue";
import { mapActions } from "pinia";
import { useKidsStore } from "../../stores/kids_store";
import { useAlertsStore } from "../../stores/alerts_store";
import { useAbsencesStore } from "../../stores/absences_store";
import Modal from "../modal/Modal.vue";
import ModalTriggerButton from "../modal/ModalTriggerButton.vue";
import { todayAsString } from "../../utils.js"

export default {
  components: { PageTitle, PageContent, Modal, ModalTriggerButton },

  props: {
    id: Number,
  },

  data() {
    return {
      kidName: null,
      absences: [],
      newAbsence: {
        start: null,
        end: null,
      },
      absenceForRemoval: null,
      absenceForUpdate: null,
      absenceUpdateType: null,
    };
  },

  computed: {
    removeAbsenceModalText() {
      if (!this.absenceForRemoval) {
        return null;
      }

      return (
        "Czy na pewno chces usunąć nieobecność dziecka " +
        this.kidName +
        " zgłoszoną w dniach od " +
        this.absenceForRemoval.start +
        " do " +
        this.absenceForRemoval.end +
        "?"
      );
    },

    updateAbsenceModalText() {
      if (!this.absenceForUpdate) {
        return null;
      }

      return (
        "Czy na pewno chcesz zmienić datę " +
        (this.absenceUpdateType == "START" ? "rozpoczęcia" : "zakończenia") +
        " nieobecności na " +
        (this.absenceUpdateType == "START"
          ? this.absenceForUpdate.start
          : this.absenceForUpdate.end) +
        "?"
      );
    },
  },

  methods: {
    ...mapActions(useKidsStore, ["getById"]),
    ...mapActions(useAlertsStore, ["showAlert"]),
    ...mapActions(useAbsencesStore, ["getAll", "create", "update", "delete"]),

    todayAsString() {
      return todayAsString();
    },

    getKidName() {
      const kid = this.getById(this.id);
      return kid.firstName + " " + kid.lastName;
    },

    loadAbsences() {
      this.absences = this.getAll(this.id);
    },

    createAbsence() {
      this.create(this.id, this.newAbsence);
      this.loadAbsences();
      this.newAbsence = {
        start: null,
        end: null,
      };

      setTimeout(function () {
        window.scrollBy(0, 1000);
      }, 100);
    },

    showUpdateAbsenceModal(typeOfChange, absence) {
      this.absenceForUpdate = absence;
      this.absenceUpdateType = typeOfChange;

      const updateAbsenceModalElement = document.getElementById("updateAbsenceModal");
      const updateAbsenceModal = new bootstrap.Modal(updateAbsenceModalElement);

      updateAbsenceModalElement.addEventListener("hidden.bs.modal", (event) => {
        this.loadAbsences();
      });

      updateAbsenceModal.show();
    },

    updateAbsence() {
      if (this.absenceForUpdate) {
        this.update(this.id, this.absenceForUpdate);
        this.absenceForUpdate = null;
        this.absenceUpdateType = null;
      }
    },

    setAbsenceForRemoval(absence) {
      this.absenceForRemoval = absence;
    },

    removeAbsence() {
      if (this.absenceForRemoval) {
        this.delete(this.id, this.absenceForRemoval);
        this.loadAbsences();
        this.absenceForRemoval = null;
      }
    },
  },

  created() {
    this.kidName = this.getKidName();
    this.loadAbsences();
  },
};
</script>

<template>
  <PageTitle title="Nieobecności" />
  <PageContent>
    <h2 class="mb-5">Nieobecności dziecka: {{ getKidName() }}</h2>
    <div class="row justify-content-center cc-absences-content">
      <div class="mb-5">
        <table class="table mb-3">
          <thead>
            <tr>
              <th scope="col">od</th>
              <th scope="col">do</th>
              <th scope="col">usuń</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="absence in absences" :key="absence.id">
              <td>
                <input
                  class="form-control"
                  type="date"
                  :min="todayAsString()"
                  v-model="absence.start"
                  @change="showUpdateAbsenceModal('START', absence)"
                />
              </td>
              <td>
                <input
                  class="form-control"
                  type="date"
                  :min="todayAsString()"
                  v-model="absence.end"
                  @change="showUpdateAbsenceModal('END', absence)"
                />
              </td>
              <td>
                <ModalTriggerButton
                  buttonClass="btn-close"
                  aria-label="Usuń nieobecność"
                  modalId="removeAbsenceModal"
                  @click="setAbsenceForRemoval(absence)"
                ></ModalTriggerButton>
              </td>
            </tr>
          </tbody>
        </table>
        <ModalTriggerButton buttonClass="btn-success" modalId="addAbsenceModal"
          >Zgłoś nieobecność</ModalTriggerButton
        >
      </div>
    </div>
  </PageContent>
  <Modal
    id="addAbsenceModal"
    title="Zgłoś nieobecność"
    actionName="Zgłoś"
    @confirmed="createAbsence"
  >
    <label for="newAbsenceStart">od:</label>
    <input
      id="newAbsenceStart"
      class="form-control mb-3"
      type="date"
      :min="todayAsString()"
      v-model="newAbsence.start"
    />

    <label for="newAbsenceEnd">do:</label>
    <input
      id="newAbsenceEnd"
      class="form-control"
      type="date"
      :min="todayAsString()"
      v-model="newAbsence.end"
    />
  </Modal>
  <Modal
    id="removeAbsenceModal"
    title="Wymagane potwierdzenie"
    :text="removeAbsenceModalText"
    actionName="Usuń"
    @confirmed="removeAbsence"
  />
  <Modal
    id="updateAbsenceModal"
    title="Wymagane potwierdzenie"
    :text="updateAbsenceModalText"
    actionName="Zmień"
    @confirmed="updateAbsence"
  />
</template>

<style scoped>
.cc-absences-content {
  width: 80%;
}
</style>