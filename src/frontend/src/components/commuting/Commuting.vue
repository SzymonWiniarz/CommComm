<script>
import PageTitle from "../page/PageTitle.vue";
import PageContent from "../page/PageContent.vue";
import { mapActions } from "pinia";
import { useTransportsStore } from "../../stores/transports_store";
import TransportCard from "./TransportCard.vue";
import DeleteTransportModal from "./DeleteTransportModal.vue";

export default {
  components: { PageTitle, PageContent, TransportCard, DeleteTransportModal },

  data() {
    return {
      transportsToSchool: [],
      transportsFromSchool: [],
      transportForDeletion: null,
    };
  },

  methods: {
    ...mapActions(useTransportsStore, [
      "getAllTransportsToSchool",
      "getAllTransportsFromSchool",
    ]),

    loadTransports() {
      this.transportsToSchool = this.getAllTransportsToSchool();
      this.transportsFromSchool = this.getAllTransportsFromSchool();
    },

    deleteTransportRequested(transportForDeletion) {
      this.transportForDeletion = transportForDeletion;
    },

    transportDeleted() {
      this.loadTransports();
      this.transportForDeletion = null;
    },
  },

  created() {
    this.loadTransports();
  },
};
</script>

<template>
  <PageTitle title="Przewozy" />
  <PageContent>
    <div class="row mb-5">
      <div class="col">
        <router-link to="/dowozy/dodaj" class="btn btn-success me-1"
          >Zadeklaruj dowóz</router-link
        >
        <router-link to="/odbiory/dodaj" class="btn btn-success"
          >Zadeklaruj odbiór</router-link
        >
      </div>
    </div>
    <div class="row mb-5">
      <div class="col">
        <h3>Moje dowozy</h3>
        <div class="row">
          <div
            class="col-auto"
            v-for="transport in transportsToSchool"
            :key="transport.id"
          >
            <TransportCard
              :transport="transport"
              @delete-transport-requested="deleteTransportRequested"
            />
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col">
        <h3>Moje odbiory</h3>
        <div class="row">
          <div
            class="col-auto"
            v-for="transport in transportsFromSchool"
            :key="transport.id"
          >
            <TransportCard
              :transport="transport"
              @delete-transport-requested="deleteTransportRequested"
            />
          </div>
        </div>
      </div>
    </div>
  </PageContent>
  <deleteTransportModal
    :transport="transportForDeletion"
    @transport-deleted="transportDeleted"
  />
</template>