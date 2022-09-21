<script>
export default {
    props: {
        buttonClass: String,
        modalId: String
    },

    computed: {
        modalIdRef() {
            return "#" + this.modalId
        }
    }
};
</script>

<template>
  <button
    type="button"
    class="btn"
    :class="buttonClass"
    data-bs-toggle="modal"
    :data-bs-target="modalIdRef"
  >
    <slot></slot>
  </button>
</template>