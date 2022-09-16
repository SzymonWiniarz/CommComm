import { defineStore } from "pinia";

export const useAlertsStore = defineStore("alerts", {

    state: () => {
        return {

        }
    },

    actions: {
        showAlert(message, type) {
            return {
                message, type
            }
        }
    }

})