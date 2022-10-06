<script>
import PageTitle from "../page/PageTitle.vue";
import PageContent from "../page/PageContent.vue";
import { mapActions } from "pinia";
import { useKidsStore } from "../../stores/kids_store";
import { useAlertsStore } from "../../stores/alerts_store";
import { useAbsencesStore } from "../../stores/absences_store";
import Modal from "../modal/Modal.vue";
import ModalTriggerButton from "../modal/ModalTriggerButton.vue";

export default {
  components: { PageTitle, PageContent, Modal, ModalTriggerButton },

  data() {
    return {
      kidName: null,
      absences: [],
      newAbsence: {
        start: null,
        end: null,
      },
    };
  },

  props: {
    id: Number,
  },

  methods: {
    ...mapActions(useKidsStore, ["getById"]),
    ...mapActions(useAlertsStore, ["showAlert"]),
    ...mapActions(useAbsencesStore, ["getAll", "create", "update", "delete"]),

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

    updateAbsence(absence) {
        this.update(this.id, absence);
        this.loadAbsences();
    },

    removeAbcence(absence) {
      this.delete(this.id, absence);
      this.loadAbsences();
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
                <input class="form-control" type="date" v-model="absence.start" @change="updateAbsence(absence)"/>
              </td>
              <td>
                <input class="form-control" type="date" v-model="absence.end" @change="updateAbsence(absence)"/>
              </td>
              <td>
                <button
                  type="button"
                  class="btn-close"
                  aria-label="Usuń nieobecność"
                  @click="removeAbcence(absence)"
                ></button>
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
      class="form-control"
      type="date"
      v-model="newAbsence.start"
    />

    <label for="newAbsenceEnd">do:</label>
    <input
      id="newAbsenceEnd"
      class="form-control"
      type="date"
      v-model="newAbsence.end"
    />
  </Modal>
</template>

<style scoped>
.cc-absences-content {
  width: 80%;
}
</style>