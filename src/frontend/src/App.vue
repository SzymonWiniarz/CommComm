<script>
import MainMenu from "./components/main_menu/MainMenu.vue";
import TopBar from "./components/TopBar.vue";
import { mapActions } from "pinia";
import { useUserStore } from "./stores/user_store";

export default {
  name: "App",
  components: { MainMenu, TopBar },

  methods: {
    ...mapActions(useUserStore, ["getById"]),
    
    getUserById(userId) {
      this.getById(userId);
    }
  },

  mounted() {
    this.getUserById(1);
  },
};
</script>

<template>
  <div class="container-fluid h-100">
    <div class="row h-100">
      <div class="col-2">
        <MainMenu />
      </div>
      <div class="col">
        <TopBar />
        <router-view />
      </div>
    </div>
  </div>
</template>