<script>
import PageContent from "../page/PageContent.vue";
import PageTitle from "../page/PageTitle.vue";
import Form from "../Form.vue";
import KidForm from "./KidForm.vue";
import { mapActions, mapState } from "pinia";
import { useUserStore } from "../../stores/user_store";
import { useAlertsStore } from "../../stores/alerts_store";
import { useKidsStore } from "../../stores/kids_store";
import Modal from "../modal/Modal.vue";
import ModalTriggerButton from "../modal/ModalTriggerButton.vue";

export default {
  components: {
    PageContent,
    PageTitle,
    Form,
    KidForm,
    Modal,
    ModalTriggerButton,
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

    kidDeletionModalText() {
      return (
        "Czy na pewno chcesz trwale usunąć informacje o dziecku " +
        this.kid.firstName +
        " " +
        this.kid.lastName +
        "?"
      );
    },
  },

  methods: {
    ...mapActions(useAlertsStore, ["showAlert"]),
    ...mapActions(useKidsStore, ["getById", "delete"]),

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
      this.$router.push({ path: "/dzieci" });
    },
  },

  created() {
    this.kid = this.getKidById();
  },
};
</script>

<template>
  <PageTitle title="Edytuj informacje o dziecku" />
  <PageContent>
    <KidForm v-if="kid" :kidParam="kid" action="update" @submitted="kidUpdated">
      <div class="row mt-3">
        <div class="col">
          <ModalTriggerButton
            buttonClass="btn-danger"
            modalId="deleteConfirmationModal"
            >Usuń</ModalTriggerButton
          >
        </div>
      </div>
    </KidForm>
  </PageContent>
  <Modal
    id="deleteConfirmationModal"
    title="Wymagane potwierdzenie"
    :text="kidDeletionModalText"
    actionName="Usuń"
    @confirmed="deleteKid"
  />
</template>