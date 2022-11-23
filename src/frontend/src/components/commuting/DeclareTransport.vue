<script>
import PageTitle from "../page/PageTitle.vue";
import PageContent from "../page/PageContent.vue";
import TransportForm from "./TransportForm.vue";
import { mapActions } from "pinia";
import { useAlertsStore } from "../../stores/alerts_store";

export default {
  components: { TransportForm, PageTitle, PageContent },

  props: {
    direction: String,
  },

  computed: {
    pageTitle() {
      return this.direction == "TO" ? "Zadeklaruj dowóz" : "Zadeklaruj odbiór";
    },
  },

  methods: {
    ...mapActions(useAlertsStore, ["showAlert"]),

    transportCreated() {
      if (this.direction == "TO") {
        this.showAlert("Zadeklarowano dowóz", "success");
      } else {
        this.showAlert("Zadeklarowano odbiór", "success");
      }

      this.$router.push({ path: "/przewozy" });
    },
  },
};
</script>

<template>
  <PageTitle :title="pageTitle" />
  <PageContent>
    <TransportForm
      action="create"
      :direction="direction"
      :transportParam="{
        direction: this.direction,
        day: null,
        lessonId: null,
        carId: null,
        seats: [],
        note: null,
      }"
      @submitted="transportCreated"
    />
  </PageContent>
</template>

