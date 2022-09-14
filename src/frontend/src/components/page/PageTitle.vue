<script>
export default {
  props: {
    title: String,
  },
};
</script>

<template>
  <div class="row mb-5">
    <h1 class="cc-title">{{ title }}</h1>
  </div>
</template>

<style scoped>
.cc-title {
  font-size: 48pt;
}
</style>