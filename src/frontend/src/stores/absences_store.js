import { defineStore } from "pinia";

function dateToString(date) {
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var year = date.getFullYear();

    if (month < 10) {
        month = "0" + month.toString();
    }

    if (day < 10) {
        day = "0" + day.toString();
    }

    return year + "-" + month + "-" + day;
}

export const useAbsencesStore = defineStore("absences", {

    state: () => {
        return {
            absences: {},
            absenceId: 1
        }
    },

    actions: {
        create(kidId, absence) {
            let absencesForKid = this.absences[kidId];

            if (!absencesForKid) {
                absencesForKid = [];
                this.absences[kidId] = absencesForKid;
            }

            let newAbsence = {
                id: this.absenceId++,
                start: new Date(absence.start),
                end: new Date(absence.end)
            };

            const overlappingEarlierAbsence = absencesForKid.find(existingAbsence => existingAbsence.start <= newAbsence.start && existingAbsence.end >= newAbsence.start);

            if (overlappingEarlierAbsence) {
                newAbsence.start.setDate(overlappingEarlierAbsence.end.getDate() + 1);
            }

            const overlappingLaterAbsence = absencesForKid.find(existingAbsence => existingAbsence.start <= newAbsence.end && existingAbsence.end >= newAbsence.end);

            if (overlappingLaterAbsence) {
                newAbsence.end.setDate(overlappingLaterAbsence.start.getDate() - 1);
            }

            absencesForKid.push(newAbsence);
        },

        update(kidId, absence) {
            let absencesForKid = this.absences[kidId];

            const absenceToUpdate = absencesForKid.find(existngAbsence => existngAbsence.id == absence.id);
            absenceToUpdate.start = new Date(absence.start);
            absenceToUpdate.end = new Date(absence.end);

            if (!absenceToUpdate) {
                return;
            }

            const overlappingEarlierAbsence = absencesForKid.find(existingAbsence => existingAbsence.id != absenceToUpdate.id 
                && existingAbsence.start <= absenceToUpdate.start 
                && existingAbsence.end >= absenceToUpdate.start);

            if (overlappingEarlierAbsence) {
                absenceToUpdate.start.setDate(overlappingEarlierAbsence.end.getDate() + 1);
            }

            const overlappingLaterAbsence = absencesForKid.find(existingAbsence => existingAbsence.id != absenceToUpdate.id 
                && existingAbsence.start <= absenceToUpdate.end 
                && existingAbsence.end >= absenceToUpdate.end);

            if (overlappingLaterAbsence) {
                absenceToUpdate.end.setDate(overlappingLaterAbsence.start.getDate() - 1);
            }
        },

        getAll(kidId) {
            const foundAbsences = this.absences[kidId];

            if (!foundAbsences) {
                return [];
            }

            return foundAbsences.map(absence => {
                return {
                    id: absence.id,
                    start: dateToString(absence.start),
                    end: dateToString(absence.end)
                }
            });
        },

        delete(kidId, absence) {
            const absencesForKid = this.absences[kidId];

            if (!absencesForKid) {
                return;
            }

            const foundAbsence = absencesForKid.find(existngAbsence => existngAbsence.id == absence.id);

            if (!foundAbsence) {
                return;
            }

            const foundAbsenceIndex = absencesForKid.indexOf(foundAbsence);
            absencesForKid.splice(foundAbsenceIndex, 1);
        },
    }

})