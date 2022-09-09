<script>
import MainMenu from "./components/main_menu/MainMenu.vue";

export default {
  name: "App",
  components: { MainMenu },
};
</script>

<template>
  <div class="container-fluid h-100 g-0">
    <div class="row h-100">
      <div class="col-2">
        <MainMenu />
      </div>
      <div class="col-10">
        <router-view />
      </div>
    </div>
  </div>
</template>