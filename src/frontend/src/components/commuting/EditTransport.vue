<script>
import PageTitle from "../page/PageTitle.vue";
import PageContent from "../page/PageContent.vue";
import TransportForm from "./TransportForm.vue";
import { mapActions } from "pinia";
import { useAlertsStore } from "../../stores/alerts_store";
import { useTransportsStore } from '../../stores/transports_store';

export default {
  components: { TransportForm, PageTitle, PageContent },

  props: {
    direction: String,
    id: Number
  },

  data() {
    return {
      transport: null
    }
  },

  computed: {
    pageTitle() {
      return this.direction == "TO" ? "Edytuj dowóz" : "Edytuj odbiór";
    },
  },

  methods: {
    ...mapActions(useAlertsStore, ["showAlert"]),
    ...mapActions(useTransportsStore, ["getTransportToSchoolById", "getTransportFromSchoolById"]),

    transportUpdated() {
      if (this.direction == "TO") {
        this.showAlert("Zmieniono dowóz", "success");
      } else {
        this.showAlert("Zmieniono odbiór", "success");
      }

      this.$router.push({ path: "/przewozy" });
    },

    getTransportById() {
      return this.direction == "TO" ? this.getTransportToSchoolById(this.id) : this.getTransportFromSchoolById(this.id);
    },

    loadTransport() {
      this.transport = this.getTransportById(this.id);
      alert(JSON.stringify(this.transport));
    }
  },

  watch: {
    id() {
      this.loadTransport();
    }
  },

  created() {
    this.loadTransport();
  },
};
</script>

<template>
  <PageTitle :title="pageTitle" />
  <PageContent>
    <TransportForm
      action="edit"
      :direction="direction"
      :transportParam="transport"
      @submitted="transportUpdated"
    />
  </PageContent>
</template>

