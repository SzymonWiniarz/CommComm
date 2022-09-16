<script>
import { useUserStore } from "@/stores/user_store";
import { mapState } from "pinia";
import { useAlertsStore } from "../stores/alerts_store";

const ALERT_TIMEOUT_MILLIS = 2000;

export default {
  name: "TopBar",

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
      this.$router.push({ path: "/uzytkownicy/" + userId });
    },

    displayAlert(alertContent) {
      this.currentAlert = alertContent;

      setTimeout(function () {
        this.currentAlert = null;
        var dismissButton = document.getElementById("alertDismissButton");
        dismissButton.click();
      }, ALERT_TIMEOUT_MILLIS);
    },
  },

  created() {
    const alertsStore = useAlertsStore();
    alertsStore.$onAction(({ name, store, args, after, onError }) => {
      after((result) => {
        if (name == "showAlert") {
          this.displayAlert(result);
        }
      });
    });
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
  <div id="liveAlertPlaceholder" class="row justify-content-center">
    <div
      v-if="currentAlert"
      class="alert alert-dismissible fade show cc-alert-box"
      :class="'alert-' + currentAlert.type"
      role="alert"
    >
      <div>{{ currentAlert.message }}</div>
      <button
        id="alertDismissButton"
        type="button"
        class="btn-close"
        data-bs-dismiss="alert"
        aria-label="Close"
      ></button>
    </div>
  </div>
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
  cursor: pointer;
}

.cc-alert-box {
  width: 50%;
  position: fixed;
  top: 10px;
}
</style>