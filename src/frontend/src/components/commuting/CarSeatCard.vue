<script>
export default {
  props: {
    modelValue: Object,
    shouldDisableKidSelection: Boolean,
    availableKids: Array,
  },

  data() {
    return {};
  },

  computed: {
    seatReservedCheckboxId() {
      return "seatReserved-" + this.seat.id;
    },

    seatType() {
      return (
        "Miejsce " + (this.seat.hasBabySeat ? "z fotelikiem" : "bez fotelika")
      );
    },

    isKidSelectionDisabled() {
      return this.seat.isReserved;
    },

    seatStatusClass() {
      if (this.seat.isReserved == true) {
        return "cc-status-reserved";
      }

      if (this.seat.kidId) {
        const selectedKid = this.availableKids.find(kid => kid.id == this.seat.kidId);

        if (selectedKid.sex == "GIRL") {
          return "cc-status-girl";
        } else {
          return "cc-status-boy";
        }
      }

      return "cc-status-free";
    },

    seat: {
      get() {
        return this.modelValue;
      },

      set(value) {
        this.$emit("update:modelValue", value);
      },
    },
  },

  watch: {
    isKidSelectionDisabled(newValue) {
      if (newValue == true) {
        this.seat.kidId = null;
      }
    },
  },

  methods: {
    getKidFullName(kid) {
      if (!kid) {
        return null;
      }

      return kid.firstName + " " + kid.lastName;
    },
  },
};
</script>

<template>
  <div class="card cc-car-seat-card">
    <div class="card-body">
      <div class="card-title">
        <h5>{{ seatType }}</h5>
        <span
          class="
            cc-seat-status
            translate-middle
            p-2
            border border-light
            rounded-circle
          "
          :class="seatStatusClass"
        >
          <span class="visually-hidden">Seat status</span>
        </span>
      </div>
      <div class="card-text">
        <div class="form-check mb-3">
          <input
            class="form-check-input"
            type="checkbox"
            v-model="seat.isReserved"
            :id="seatReservedCheckboxId"
          />
          <label :for="seatReservedCheckboxId">Zajęte</label>
        </div>
        <div>
          <select
            class="form-select"
            aria-label="Wybierz dziecko"
            :disabled="isKidSelectionDisabled"
            v-model="seat.kidId"
          >
            <option selected value="null">Wybierz dziecko</option>
            <option v-for="kid in availableKids" :value="kid.id" :key="kid.id">
              {{ getKidFullName(kid) }}
            </option>
          </select>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.cc-seat-status {
  position: absolute;
  top: 20%;
  right: 0;
}

.cc-status-reserved {
  background-color: $color-danger;
}

.cc-status-free {
  background-color: $color-success;
}

.cc-status-girl {
  background-color: $color-pink;
}

.cc-status-boy {
  background-color: $color-cyan;
}
</style>