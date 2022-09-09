<script>
import MainMenu from "./components/main_menu/MainMenu.vue";
import TopBar from "./components/TopBar.vue";

export default {
  name: "App",
  components: { MainMenu, TopBar },
};
</script>

<template>
  <div class="container-fluid h-100">
    <div class="row h-100">
      <div class="col-2">
        <MainMenu />
      </div>
      <div class="col">
        <TopBar userFirstName="Szymon" userLastName="Winiarz" />
        <router-view />
      </div>
    </div>
  </div>
</template>