import { defineStore } from "pinia";

export const useSchedulesStore = defineStore("schedules", {

    state: () => {
        return {
            schedules: {}
        }
    },

    actions: {
        saveSchedule(kidId, schedule) {
            this.schedules[kidId] = schedule;
        },

        getSchedule(kidId) {
            const foundSchedule = this.schedules[kidId];

            if (!foundSchedule) {
                return [
                    {
                        dayId: "MONDAY",
                        start: null,
                        end: null,
                    },
                    {
                        dayId: "TUESDAY",
                        start: null,
                        end: null,
                    },
                    {
                        dayId: "WEDNESDAY",
                        start: null,
                        end: null,
                    },
                    {
                        dayId: "THURSDAY",
                        start: null,
                        end: null,
                    },
                    {
                        dayId: "FRIDAY",
                        start: null,
                        end: null,
                    },
                ];
            }

            return foundSchedule.map(day => Object.assign({}, day));
        }
    }

})