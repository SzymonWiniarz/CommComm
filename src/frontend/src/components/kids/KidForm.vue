<script>
import Form from "../Form.vue";
export default {
  components: { Form },

  props: {
    kidParam: Object,
  },

  data() {
    return {
      kid: this.kidParam,
    };
  },

  methods: {
    saveKidForm() {
      alert("Dodano dziecko!");
    },
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
    </Form>
  </div>
</template>

<style scoped>
#kidForm {
  width: 80%;
}
</style>