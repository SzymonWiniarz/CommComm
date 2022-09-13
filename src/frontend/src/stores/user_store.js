import { defineStore } from "pinia";

export const useUserStore = defineStore("user", {

    state: () => {
        return {
            user: null
        }
    },

    actions: {
        getById(idParam) {
            this.user = {
                id: idParam,
                firstName: "Szymon",
                lastName: "Winiarz",
                email: "sz@w.pl",
                phone: "111 111 111",
                canNotifyBySms: false,
                canNotifyByWhatsApp: false,
                canNotifyByEmail: true
            }
        },

        update(newFirstName, newLastName) {
            this.firstName = newFirstName;
            this.lastName = newLastName;
        }
    }

})