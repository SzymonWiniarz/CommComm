import { defineStore } from "pinia";

export const useUserStore = defineStore("user", {

    state: () => {
        return {
            user: {
                id: 1,
                firstName: "Szymon",
                lastName: "Winiarz",
                email: "sz@w.pl",
                phone: "111 111 111",
                canNotifyBySms: false,
                canNotifyByWhatsApp: false,
                canNotifyByEmail: true
            }
        }
    },

    getters: {
        getUser() {
            return Object.assign({}, this.user);
        }
    },

    actions: {
        update(updatedUser) {
            this.user = updatedUser;
        }
    }

})