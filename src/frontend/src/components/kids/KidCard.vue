<script>
export default {
  name: "KidCard",

  props: {
    kid: Object
  },

  methods: {
    getSexText() {
      if (this.kid.sex === "GIRL") {
        return "Dziewczynka"
      }

      if (this.kid.sex === "BOY") {
        return "Chłopiec"
      }

      return ""
    }
  }
}
</script>

<template>
  <div class="card cc-kid-card">
    <div class="card-body">
      <h5 class="card-title">{{ kid.firstName + " " + kid.lastName }}</h5>
      <p class="card-text">
        <span class="badge" :class="kid.sex.toLowerCase()">{{ this.getSexText() }}</span>
        <span v-if="kid.doesNeedBabySeat" class="badge bg-secondary">Fotelik</span>
      </p>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.cc-kid-card {
  cursor: pointer;
}

.badge.girl {
  background-color: $color-pink;
}

.badge.boy {
  background-color: $color-cyan;
}
</style>