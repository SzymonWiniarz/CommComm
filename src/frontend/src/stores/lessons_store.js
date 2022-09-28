import { defineStore } from "pinia";

export const useLessonsStore = defineStore("lessons", {

    state: () => {
        return {
            lessons: [
                {
                    id: 1,
                    startsAt: "8:15",
                    endsAt: "9:00"
                },
                {
                    id: 2,
                    startsAt: "9:05",
                    endsAt: "9:50"
                },
                {
                    id: 3,
                    startsAt: "10:00",
                    endsAt: "10:45"
                },
                {
                    id: 4,
                    startsAt: "11:05",
                    endsAt: "11:50"
                },
                {
                    id: 5,
                    startsAt: "11:55",
                    endsAt: "12:40"
                },
                {
                    id: 6,
                    startsAt: "13:00",
                    endsAt: "13:45"
                },
                {
                    id: 7,
                    startsAt: "14:05",
                    endsAt: "14:50"
                },
                {
                    id: 8,
                    startsAt: "15:00",
                    endsAt: "16:00"
                },
                {
                    id: 9,
                    startsAt: "15:00",
                    endsAt: "16:30"
                },
            ]
        }
    },

    actions: {
        getAllLessons() {
            return this.lessons;
        }
    }

})