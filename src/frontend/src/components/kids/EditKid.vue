<script>
import PageContent from "../page/PageContent.vue";
import PageTitle from "../page/PageTitle.vue";
import Form from "../Form.vue";
import KidForm from "./KidForm.vue";
import { mapActions, mapState } from "pinia";
import { useUserStore } from "../../stores/user_store";
import { useAlertsStore } from "../../stores/alerts_store";
import { useKidsStore } from "../../stores/kids_store";
import ModalTriggerButton from "../modal/ModalTriggerButton.vue";
import DeleteKidModal from "./DeleteKidModal.vue";

export default {
  components: {
    PageContent,
    PageTitle,
    Form,
    KidForm,
    ModalTriggerButton,
    DeleteKidModal,
  },

  props: {
    id: Number,
  },

  data() {
    return {
      kid: null,
    };
  },

  computed: {
    ...mapState(useUserStore, {
      user: "getUser",
    }),
  },

  methods: {
    ...mapActions(useAlertsStore, ["showAlert"]),
    ...mapActions(useKidsStore, ["getById"]),

    kidUpdated() {
      this.showAlert(
        "Pomyślnie zaktualizowano informacje o dziecku",
        "success"
      );
      this.$router.push({ path: "/dzieci" });
    },

    getKidById() {
      return this.getById(this.id);
    },

    loadKid() {
      this.kid = this.getKidById();
    },
  },

  watch: {
    id() {
      this.loadKid();
    },
  },

  created() {
    this.loadKid();
  },
};
</script>

<template>
  <PageTitle title="Edytuj informacje o dziecku" />
  <PageContent>
    <div class="row mb-5 justify-content-center">
      <div class="cc-kid-actions">
        <ModalTriggerButton
          buttonClass="btn-danger me-1"
          modalId="deleteConfirmationModal"
          >Usuń</ModalTriggerButton
        >
        <router-link :to="'/dzieci/' + kid.id + '/plan'" class="btn btn-secondary">Edytuj plan lekcji</router-link>
      </div>
    </div>
    <KidForm
      v-if="kid"
      :kidParam="kid"
      action="update"
      @submitted="kidUpdated"
    />
    <div v-else class="row">
      <div class="col">
        Nie znaleziono dziecka. Wróć do
        <router-link to="/dzieci">listy swoich dzieci</router-link> i wybierz
        jedno z nich lub dodaj nowe.
      </div>
    </div>
  </PageContent>
  <DeleteKidModal :kid="kid" />
</template>

<style scoped>
.cc-kid-actions {
  width: 80%;
}
</style>