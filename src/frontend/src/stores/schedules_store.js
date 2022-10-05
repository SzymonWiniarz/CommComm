import { defineStore } from "pinia";

export const useSchedulesStore = defineStore("schedules", {

    state: () => {
        return {
            standardSchedules: {},
            customSchedules: {},
        }
    },

    actions: {
        saveStandardSchedule(kidId, schedule) {
            this.standardSchedules[kidId] = schedule;
        },

        getStandardSchedule(kidId) {
            const foundSchedule = this.standardSchedules[kidId];

            if (!foundSchedule) {
                return [
                    {
                        dayId: "MONDAY",
                        start: null,
                        end: null,
                        canDeliverEarlier: false,
                        canPickUpLater: false
                    },
                    {
                        dayId: "TUESDAY",
                        start: null,
                        end: null,
                        canDeliverEarlier: false,
                        canPickUpLater: false
                    },
                    {
                        dayId: "WEDNESDAY",
                        start: null,
                        end: null,
                        canDeliverEarlier: false,
                        canPickUpLater: false
                    },
                    {
                        dayId: "THURSDAY",
                        start: null,
                        end: null,
                        canDeliverEarlier: false,
                        canPickUpLater: false
                    },
                    {
                        dayId: "FRIDAY",
                        start: null,
                        end: null,
                        canDeliverEarlier: false,
                        canPickUpLater: false
                    },
                ];
            }

            return foundSchedule.map(day => Object.assign({}, day));
        },

        saveCustomSchedule(kidId, schedule) {
            this.customSchedules[kidId] = schedule;
        },

        getCustomSchedule(kidId) {
            const foundSchedule = this.customSchedules[kidId];

            if (!foundSchedule) {
                return [];
            }

            return foundSchedule.map(day => Object.assign({}, day));
        }
    }

})