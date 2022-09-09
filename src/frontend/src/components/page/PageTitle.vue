<script>
export default {
  props: {
    title: String,
  },
};
</script>

<template>
  <div class="row">
    <h1 class="cc-title">{{ title }}</h1>
  </div>
</template>

<style scoped>
.cc-title {
  font-size: 48pt;
  margin-bottom: 50px;
}
</style>