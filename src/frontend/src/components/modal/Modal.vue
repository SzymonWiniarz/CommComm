<script>
export default {
  props: {
    id: String,
    title: String,
    text: String,
    actionName: String
  },

  computed: {
    modalLabel() {
      return this.id + "Label";
    },
  },

  methods: {
    confirm() {
        this.$emit("confirmed");
    }
  },

  emits: ["confirmed"]
};
</script>

<template>
  <div
    class="modal fade"
    :id="id"
    tabindex="-1"
    :aria-labelledby="modalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" :id="modalLabel">{{ title }}</h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          {{ text }}
        </div>
        <div class="modal-footer">
          <button
            type="button"
            class="btn btn-secondary"
            data-bs-dismiss="modal"
          >
            Zamknij
          </button>
          <button
            type="button"
            class="btn btn-danger"
            @click="confirm"
            data-bs-dismiss="modal"
          >
            {{ actionName }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>