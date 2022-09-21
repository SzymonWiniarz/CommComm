<script>
import Form from "../Form.vue";
import { mapActions } from "pinia";
import { useKidsStore } from "../../stores/kids_store";

export default {
  components: { Form },

  props: {
    kidParam: Object,
    action: String,
  },

  data() {
    return {
      kid: this.kidParam,
    };
  },

  methods: {
    ...mapActions(useKidsStore, {
      createKidAction: "create",
      updateKidAction: "update",
    }),

    saveKidForm() {
      if (this.action == "create") {
        this.createKidAction(this.kid);
      } else {
        this.updateKidAction(this.kid);
      }

      this.$emit("submitted");
    },

    emits: ["submitted"],
  },
};
</script>

<template>
  <div class="row justify-content-center">
    <Form id="kidForm" :onSubmit="saveKidForm">
      <div class="row mb-3">
        <div class="col">
          <label for="kidFirstName" class="form-label">Imię</label>
          <input
            id="kidFirstName"
            class="form-control"
            v-model="kid.firstName"
            required
          />
          <div class="invalid-feedback">Podaj imię</div>
        </div>
        <div class="col">
          <label for="kidLastName" class="form-label">Nazwisko</label>
          <input
            id="kidLastName"
            class="form-control"
            v-model="kid.lastName"
            required
          />
          <div class="invalid-feedback">Podaj nazwisko</div>
        </div>
      </div>
      <div class="row mb-3">
        <div class="col">
          <div class="form-check">
            <input
              class="form-check-input"
              type="radio"
              name="sex"
              id="sexBoy"
              value="BOY"
              v-model="kid.sex"
              required
            />
            <label class="form-check-label" for="sexBoy">Chłopiec</label>
          </div>
          <div class="form-check">
            <input
              class="form-check-input"
              type="radio"
              name="sex"
              id="sexGirl"
              value="GIRL"
              v-model="kid.sex"
              required
            />
            <label class="form-check-label" for="sexGirl">Dziewczynka</label>
            <div class="invalid-feedback">Podaj płeć</div>
          </div>
        </div>
      </div>
      <div class="row mb-3">
        <div class="col">
          <div class="form-check">
            <input
              class="form-check-input"
              type="checkbox"
              v-model="kid.doesNeedBabySeat"
              id="kidNeedsBabySeat"
            />
            <label for="kidNeedsCarSeat">Potrzebuje fotelik</label>
          </div>
        </div>
      </div>
      <button type="submit" class="btn btn-primary mb-3">Zapisz</button>
      <slot></slot>
    </Form>
  </div>
</template>

<style scoped>
#kidForm {
  width: 80%;
}
</style>