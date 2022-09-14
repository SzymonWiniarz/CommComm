<script>
export default {
  props: {
    id: String,
    onSubmit: Function,
  },

  methods: {
    validateForm(event) {
      // Fetch all the forms we want to apply custom Bootstrap validation styles to
      var form = document.getElementById(this.id);

      if (form) {
        let isFormValid = true;

        if (!form.checkValidity()) {
          event.preventDefault();
          event.stopPropagation();
          isFormValid = false;
        }

        form.classList.add("was-validated");

        return isFormValid;
      }

      return true;
    },

    clearValidation() {
      var form = document.getElementById(this.id);

      if (form) {
        form.classList.remove("was-validated");
      }
    },
  },
};
</script>

<template>
  <form
    :id="id"
    class="justify-content-center needs-validation"
    @submit.prevent="
      (event) => validateForm(event) && onSubmit() & clearValidation()
    "
    novalidate
  >
    <slot></slot>
  </form>
</template>