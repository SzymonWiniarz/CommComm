<script>
import MenuItem from './MenuItem.vue';

export default {
    name: "MainMenu",
    components: { MenuItem }
};
</script>

<template>
  <div class="cc-main-menu h-100">
    <div class="row text-center">
      <img class="cc-logo" src="../../assets/images/bus-logo.png" />
      <div class="cc-logo-title">CommComm</div>
    </div>
    <MenuItem target="/" text="Pulpit" />
    <MenuItem target="/dzieci" text="Dzieci" />
    <MenuItem target="/samochody" text="Samochody" />
    <MenuItem target="/przewozy" text="Przewozy" />
  </div>
</template>

<style lang="scss" scoped>
.cc-main-menu {
  background-color: $color-primary;
  color: white;
}

.cc-logo {
  width: 80%;
  margin: 15px;
}

.cc-logo-title {
    font-size: 24pt;
    margin-bottom: 50px;
}
</style>