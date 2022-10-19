<script>

export default {
  props: {
    type: String,
    modelValue: Number,
    lessons: Array,
  },

  computed: {
    pickerLabel() {
      return (
        "Wybierz godzinę " +
        (this.type == "START" ? "rozpoczęcia" : "zakończenia") +
        " lekcji"
      );
    },

    inputValue: {
      get() {
        return this.modelValue;
      },

      set(value) {
        this.$emit("update:modelValue", value);
      },
    },
  },
};
</script>

<template>
  <select class="form-select" :aria-label="pickerLabel" v-model="inputValue">
    <option disabled selected value="null">
      {{ pickerLabel }}
    </option>
    <option v-for="lesson in lessons" :value="lesson.id" :key="lesson.id">
      {{ type == "START" ? lesson.startsAt : lesson.endsAt }}
    </option>
  </select>
</template>