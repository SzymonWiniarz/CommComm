<script>
import {useUserStore} from "@/stores/user_store";
import {mapState} from "pinia";
import AlertsContainer from "@/components/AlertsContainer.vue";

export default {
  name: "TopBar",

  components: {AlertsContainer},

  data() {
    return {
      currentAlert: null,
    };
  },

  computed: {
    ...mapState(useUserStore, ["user"]),

    userInitials() {
      if (!this.user) {
        return "N/A";
      }

      return (
          this.user.firstName[0].toUpperCase() +
          this.user.lastName[0].toUpperCase()
      );
    },
  },

  methods: {
    openEditUserView() {
      const userId = this.user.id;
      this.$router.push({path: "/uzytkownicy/" + userId});
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
      <div class="col-12" @click="openEditUserView">
        {{ userInitials }}
      </div>
    </div>
  </div>
  <AlertsContainer />
</template>

<style lang="scss" scoped>
.cc-user-icon {
  height: 50px;
  width: 50px;
  display: inline-block;
  text-align: center;
  background-color: $color-info;
  border-radius: 50%;
  margin: 10px;
  font-size: 18pt;
  color: white;
  padding: 0;
}
</style>