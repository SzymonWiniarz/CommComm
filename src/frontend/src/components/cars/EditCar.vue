<script>
import PageContent from "../page/PageContent.vue";
import PageTitle from "../page/PageTitle.vue";
import Form from "../Form.vue";
import CarForm from "./CarForm.vue";
import { mapActions } from "pinia";
import { useAlertsStore } from "../../stores/alerts_store";
import { useCarsStore } from "../../stores/cars_store";
import ModalTriggerButton from "../modal/ModalTriggerButton.vue";
import DeleteCarModal from "./DeleteCarModal.vue";

export default {
  components: {
    PageContent,
    PageTitle,
    Form,
    CarForm,
    ModalTriggerButton,
    DeleteCarModal
},

  props: {
    id: Number,
  },

  data() {
    return {
      car: null,
    };
  },

  methods: {
    ...mapActions(useAlertsStore, ["showAlert"]),
    ...mapActions(useCarsStore, ["getById"]),

    carUpdated() {
      this.showAlert(
        "Pomyślnie zaktualizowano informacje o samochodzie",
        "success"
      );
      this.$router.push({ path: "/samochody" });
    },

    getCarById() {
      return this.getById(this.id);
    },

    loadCar() {
      this.car = this.getCarById(this.id);
    }
  },

  watch: {
    id() {
      this.loadCar();
    }
  },

  created() {
    this.loadCar();
  },
};
</script>

<template>
  <PageTitle title="Edytuj informacje o samochodzie" />
  <PageContent>
    <CarForm v-if="car" :carParam="car" action="update" @submitted="carUpdated">
      <div class="row mt-3">
        <div class="col">
          <ModalTriggerButton buttonClass="btn-danger" modalId="deleteConfirmationModal"
            >Usuń</ModalTriggerButton
          >
        </div>
      </div>
    </CarForm>
    <div v-else class="row">
      <div class="col">
        Nie znaleziono samochodu. Wróć do
        <router-link to="/samochody">listy swoich samochodów</router-link> i
        wybierz jeden z nich lub dodaj nowy.
      </div>
    </div>
  </PageContent>
  <DeleteCarModal :car="car" />
</template>