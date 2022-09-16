import { defineStore } from "pinia";

export const useKidsStore = defineStore("kids", {

    state: () => {
        return {
            kids: [],
            kidId: 1
        }
    },

    actions: {
        create(kid) {
            const nextId = (this.kidId++).toString;
            kid.id = nextId;
            this.kids.push(kid);
        },

        delete(id) {
            this.kids = this.kids.filter((kid) => kid.id != id);
        },

        getById(id) {
            return Object.assign({}, this.kids.find((kid) => kid.id == id));
        },

        getAll() {
            return this.kids.map((kid) => Object.assign({}, kid));
        },

        update(kid) {
            this.delete(kid.id);
            this.kids.push(kid);
        }
    }

})