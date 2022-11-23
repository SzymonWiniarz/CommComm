<script>
export default {
    props: {
        title: String
    }
};
</script>

<template>
  <div class="card cc-dashboard-widget-card mb-3">
    <div class="card-body">
      <div class="card-title mb-5">
        <h2>{{title}}</h2>
      </div>
      <div class="card-text">
        <slot></slot>
      </div>
    </div>
  </div>
</template>

<style scoped>
.cc-dashboard-widget-card {
    width: 95%;
}
</style>