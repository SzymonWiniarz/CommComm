<script>
import PageTitle from "../page/PageTitle.vue";
import PageContent from "../page/PageContent.vue";
import KidCard from "@/components/kids/KidCard.vue";
import { useKidsStore } from "@/stores/kids_store";
import { mapActions } from "pinia";
import DeleteKidModal from "./DeleteKidModal.vue";

export default {
  components: { PageTitle, PageContent, KidCard, DeleteKidModal },

  data() {
    return {
      kids: [],
      kidForDeletion: null,
    };
  },

  methods: {
    ...mapActions(useKidsStore, {
      getAllKids: "getAll",
    }),

    deleteKidRequested(kidForDeletion) {
      this.kidForDeletion = kidForDeletion;
    },

    loadKids() {
      this.kids = this.getAllKids();
    },

    kidDeleted() {
      this.loadKids();
      this.kidForDeletion = null;
    }
  },

  created() {
    this.loadKids();
  },
};
</script>

<template>
  <PageTitle title="Dzieci" />
  <PageContent>
    <div class="row mb-5">
      <div class="col">
        <router-link to="/dzieci/dodaj" class="btn btn-success"
          >Dodaj</router-link
        >
      </div>
    </div>
    <div class="row mb3">
      <div class="col-auto" v-for="kid in kids" :key="kid.id">
        <KidCard :kid="kid" @delete-kid-requested="deleteKidRequested" />
      </div>
    </div>
  </PageContent>
  <DeleteKidModal :kid="kidForDeletion" @kid-deleted="kidDeleted" />
</template>