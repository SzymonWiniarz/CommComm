<script>
import PageContent from "../page/PageContent.vue";
import PageTitle from "../page/PageTitle.vue";
import Form from "../Form.vue";
import CarForm from "./CarForm.vue";
import { mapActions, mapState } from "pinia";
import { useAlertsStore } from "../../stores/alerts_store";

export default {
  components: { PageContent, PageTitle, Form, CarForm },

  methods: {
    ...mapActions(useAlertsStore, ["showAlert"]),

    carCreated() {
      this.showAlert("Dodano nowy samochód", "success");
      this.$router.push({ path: "/samochody" });
    },
  },
};
</script>

<template>
  <PageTitle title="Dodaj samochód" />
  <PageContent>
    <CarForm
      :carParam="{}"
      action="create"
      @submitted="carCreated"
    />
  </PageContent>
</template>