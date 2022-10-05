<script>
export default {
  props: {
    checked: Boolean,
  },

  methods: {
    updateChecked(event) {
        this.$emit('update:checked', event.target.checked);
    }
  }
};
</script>

<template>
  <div class="form-check cc-schedule-checkbox-container">
    <input
      class="form-check-input cc-schedule-checkbox"
      type="checkbox"
      :checked="checked"
      @input="updateChecked"
    />
  </div>
</template>

<style scoped>
.cc-schedule-checkbox-container {
  text-align: center;
}

.cc-schedule-checkbox {
  float: none;
}
</style>