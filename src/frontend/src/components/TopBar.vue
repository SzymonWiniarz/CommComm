<script>
export default {
  name: "TopBar",

  props: {
    userFirstName: String,
    userLastName: String,
  },

  computed: {
    userInitials() {
      return (
        this.userFirstName[0].toUpperCase() + this.userLastName[0].toUpperCase()
      );
    },
  },
};
</script>

<template>
  <div class="row justify-content-end">
    <div
      class="
        d-flex
        row
        g-0
        col-1
        cc-user-icon
        align-items-center
        justify-content-center
      "
    >
      <div class="col-12">
        {{ userInitials }}
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.cc-user-icon {
  height: 50px;
  width: 50px;
  display: inline-block;
  text-align: center;
  background-color: $color-camel;
  border-radius: 50%;
  margin: 10px;
  font-size: 18pt;
  color: white;
  padding: 0;
}
</style>