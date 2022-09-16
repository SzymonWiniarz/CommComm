<script>
import {useAlertsStore} from "@/stores/alerts_store";

const ALERT_TIMEOUT_MILLIS = 2000;

const ALERT_TEMPLATE = `
<div class="alert alert-{{type}} alert-dismissible fade show"
     role="alert"
>
    <div>{{message}}</div>
    <button
        id="alertDismissButton-{{id}}"
        type="button"
        class="btn-close"
        data-bs-dismiss="alert"
        aria-label="Close"
    ></button>
  </div>
`;

function generateAlertId() {
  return (Math.random() + 1).toString(36).substring(7);
}

function fillAlertTemplate(alertId, type, message) {
  return ALERT_TEMPLATE
      .replace("{{type}}", type)
      .replace("{{message}}", message)
      .replace("{{id}}", alertId);
}

function dismissAlert(alertId) {
  const dismissButton = document.getElementById("alertDismissButton-" + alertId);
  dismissButton.click();
}

export default {
  name: "AlertsContainer",

  methods: {
    displayAlert(alertContent) {
      const alertId = generateAlertId();

      this.$refs.liveAlertPlaceholder.innerHTML += fillAlertTemplate(alertId, alertContent.type, alertContent.message);

      setTimeout(function () {
        dismissAlert(alertId);
      }, ALERT_TIMEOUT_MILLIS);
    }
  },

  created() {
    const alertsStore = useAlertsStore();
    alertsStore.$onAction(({name, store, args, after, onError}) => {
      after((result) => {
        if (name === "showAlert") {
          this.displayAlert(result);
        }
      });
    });
  },
}
</script>

<template>
  <div class="row justify-content-center">
    <div ref="liveAlertPlaceholder" class="cc-alerts-stack">

    </div>
  </div>
</template>

<style>
.cc-alerts-stack {
  width: 50%;
  position: fixed;
  top: 10px;
}
</style>