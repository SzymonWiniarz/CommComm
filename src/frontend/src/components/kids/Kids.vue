<script>
import PageTitle from "../page/PageTitle.vue";
import PageContent from "../page/PageContent.vue";
import KidCard from "@/components/kids/KidCard.vue";
import {useKidsStore} from "@/stores/kids_store";
import {mapActions} from "pinia";

export default {
  components: { PageTitle, PageContent, KidCard },

  data() {
    return {
      kids: []
    }
  },

  methods: {
    ...mapActions(useKidsStore, {
      getAllKids: "getAll"
    })
  },

  mounted() {
    this.kids = this.getAllKids();
  }
};
</script>

<template>
  <PageTitle title="Dzieci" />
  <PageContent>
    <div class="row mb-5">
      <div class="col">
        <router-link to="/dzieci/dodaj" class="btn btn-success">Dodaj</router-link>
      </div>
    </div>
    <div class="row mb3">
      <div class="col-auto" v-for="kid in kids" :key="kid.id">
        <KidCard :kid="kid"/>
      </div>
    </div>
  </PageContent>
</template>