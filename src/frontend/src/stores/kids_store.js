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
            const nextId = (this.kidId++).toString();
            kid.id = nextId;
            this.kids.push(kid);
        },

        delete(id) {
            this.kids = this.kids.filter((kid) => kid.id != id);
        },

        update(kid) {
            this.delete(kid.id);
            this.kids.push(kid);
        },

        getById(id) {
            const foundKid = this.kids.find((kid) => kid.id == id);

            if (foundKid) {
                return Object.assign({}, foundKid);
            }
        },

        getAll() {
            return this.kids.map((kid) => Object.assign({}, kid));
        },

        getAllForCommuting() {
            const myKids = this.getAll();
            const otherKids = [
                {
                    id: 100,
                    firstName: "Franek",
                    lastName: "Koc",
                    sex: "BOY",
                    doesNeedBabySeat: false
                },
                {
                    id: 101,
                    firstName: "Faustyna",
                    lastName: "Koc",
                    sex: "GIRL",
                    doesNeedBabySeat: true
                },
                {
                    id: 102,
                    firstName: "Łucja",
                    lastName: "Koc",
                    sex: "GIRL",
                    doesNeedBabySeat: true
                },
                {
                    id: 103,
                    firstName: "Marianna",
                    lastName: "Książek",
                    sex: "GIRL",
                    doesNeedBabySeat: false
                }
            ]

            return myKids.concat(otherKids);
        }
    }

})