<script>
import PageContent from "../page/PageContent.vue";
import PageTitle from "../page/PageTitle.vue";
import Form from "../Form.vue";
import CarForm from "./CarForm.vue";
import { mapActions, mapState } from "pinia";
import { useAlertsStore } from "../../stores/alerts_store";
import { useCarsStore } from "../../stores/cars_store";
import Modal from "../modal/Modal.vue";
import ModalTriggerButton from "../modal/ModalTriggerButton.vue";

export default {
  components: {
    PageContent,
    PageTitle,
    Form,
    CarForm,
    Modal,
    ModalTriggerButton,
  },

  props: {
    id: Number,
  },

  data() {
    return {
      car: null,
    };
  },

  computed: {
    deleteCarModalText() {
      return (
        "Czy na pewno chcesz trwale usunąć informacje o samochodzie " +
        this.car.name +
        "?"
      );
    },
  },

  methods: {
    ...mapActions(useAlertsStore, ["showAlert"]),
    ...mapActions(useCarsStore, ["getById", "delete"]),

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

    deleteCar() {
      this.delete(this.car.id);
      this.showAlert(
        "Pomyślnie usunięto samochód " + this.car.name + " z listy",
        "success"
      );
      this.$router.push({ path: "/samochody" });
    },
  },

  created() {
    this.car = this.getCarById();
  },
};
</script>

<template>
  <PageTitle title="Edytuj informacje o samochodzie" />
  <PageContent>
    <CarForm v-if="car" :carParam="car" action="update" @submitted="carUpdated">
      <div class="row mt-3">
        <div class="col">
          <ModalTriggerButton buttonClass="btn-danger" modalId="deleteCarModal"
            >Usuń</ModalTriggerButton
          >
        </div>
      </div>
    </CarForm>
  </PageContent>
  <Modal
    id="deleteCarModal"
    title="Wymagane potwierdzenie"
    :text="deleteCarModalText"
    actionName="Usuń"
    @confirmed="deleteCar"
  />
</template>