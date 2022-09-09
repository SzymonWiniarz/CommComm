<script>
export default {
  props: {
    target: String,
    text: String,
  },
};
</script>

<template>
  <div class="row cc-menu-item text-center g-0">
    <router-link :to="target">{{ text }}</router-link>
  </div>
</template>

<style lang="scss" scoped>
.cc-menu-item:hover,
.router-link-active {
  background-color: $color-riffle-green;
}

.cc-menu-item > a {
  color: white;
  text-decoration: none;
  display: block;
  height: 100%;
  padding: 10px 0;
  font-size: 18pt;
}
</style>