<script>
import PageTitle from "../page/PageTitle.vue";
import PageContent from "../page/PageContent.vue";
import { mapActions } from "pinia";
import { useKidsStore } from "../../stores/kids_store";
import { useLessonsStore } from "../../stores/lessons_store";
import { useSchedulesStore } from "../../stores/schedules_store";
import { useAlertsStore } from "../../stores/alerts_store";
import LessonPicker from "../lessons/LessonPicker.vue";
import DeliveryCheckbox from "./DeliveryCheckbox.vue";
import { todayAsString } from "../../utils.js";

function generateRandomId() {
  return (Math.random() + 1).toString(36).substring(7);
}

export default {
  components: { PageTitle, PageContent, LessonPicker, DeliveryCheckbox },

  props: {
    id: Number,
  },

  data() {
    return {
      kidName: null,
      lessons: [],
      daysOfWeek: [
        {
          id: "MONDAY",
          name: "Poniedziałek",
        },
        {
          id: "TUESDAY",
          name: "Wtorek",
        },
        {
          id: "WEDNESDAY",
          name: "Środa",
        },
        {
          id: "THURSDAY",
          name: "Czwartek",
        },
        {
          id: "FRIDAY",
          name: "Piątek",
        },
      ],

      standardSchedule: [],
      customSchedule: [],
    };
  },

  methods: {
    ...mapActions(useKidsStore, ["getById"]),
    ...mapActions(useLessonsStore, ["getAllLessons"]),
    ...mapActions(useSchedulesStore, [
      "saveStandardSchedule",
      "getStandardSchedule",
      "saveCustomSchedule",
      "getCustomSchedule",
    ]),
    ...mapActions(useAlertsStore, ["showAlert"]),

    todayAsString() {
      return todayAsString();
    },

    getKidName() {
      const kid = this.getById(this.id);
      return kid.firstName + " " + kid.lastName;
    },

    saveStandardScheduleTable() {
      this.saveStandardSchedule(this.id, this.standardSchedule);
      this.showAlert(
        "Pomyślnie zaktualizowano standardowy plan lekcji",
        "success"
      );
    },

    saveCustomScheduleTable() {
      this.saveCustomSchedule(this.id, this.customSchedule);
      this.showAlert(
        "Pomyślnie zaktualizowano niestandardowy plan lekcji",
        "success"
      );
    },

    addCustomScheduleEntry() {
      const newScheduleEntry = {
        id: generateRandomId(),
        date: null,
        start: null,
        end: null,
        canDeliverEarlier: false,
        canPickUpLater: false
      };

      this.customSchedule.push(newScheduleEntry);

      setTimeout(function () {
        window.scrollBy(0, 1000);
      }, 100);
    },

    getDayOfWeekFromSchedule(scheduleDay) {
      return this.daysOfWeek.find((dayOfWeek) => dayOfWeek.id == scheduleDay.dayId).name;
    },

    getDayOfWeekFromDate(dateString) {
      if (dateString) {
        const dayOfWeekIndex = new Date(dateString).getDay();
        switch (dayOfWeekIndex) {
          case 0:
            return "Niedziela";
          case 1:
            return this.daysOfWeek.find((day) => day.id == "MONDAY").name;
          case 2:
            return this.daysOfWeek.find((day) => day.id == "TUESDAY").name;
          case 3:
            return this.daysOfWeek.find((day) => day.id == "WEDNESDAY").name;
          case 4:
            return this.daysOfWeek.find((day) => day.id == "THURSDAY").name;
          case 5:
            return this.daysOfWeek.find((day) => day.id == "FRIDAY").name;
          case 6:
            return "Sobota";
          default:
            return "Dzień Świstaka";
        }
      }
    },

    removeCustomDay(customDay) {
      const indexOfDay = this.customSchedule.indexOf(customDay);

      if (indexOfDay > -1) {
        this.customSchedule.splice(indexOfDay, 1);
      }
    },
  },

  created() {
    this.kidName = this.getKidName();
    this.lessons = this.getAllLessons();
    this.standardSchedule = this.getStandardSchedule(this.id);
    this.customSchedule = this.getCustomSchedule(this.id);
  },
};
</script>

<template>
  <PageTitle title="Edytuj plan lekcji" />
  <PageContent>
    <h2 class="mb-5">Plan lekcji dla: {{ kidName }}</h2>
    <div class="row justify-content-center cc-schedule-content">
      <div class="cc-schedule-content mb-5">
        <h3 class="mb-3">Standardowy plan lekcji</h3>
        <table class="table mb-3">
          <thead>
            <tr>
              <th scope="col">Dzień tygodnia</th>
              <th scope="col">Godzina rozpoczęcia</th>
              <th scope="col">Godzina zakończenia</th>
              <th scope="col">Można przywieźć wcześniej</th>
              <th scope="col">Można odebrać później</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="scheduleDay in standardSchedule" :key="scheduleDay.id">
              <td>{{ getDayOfWeekFromSchedule(scheduleDay) }}</td>
              <td>
                <LessonPicker
                  type="START"
                  :lessons="lessons"
                  v-model="scheduleDay.start"
                />
              </td>
              <td>
                <LessonPicker
                  type="END"
                  :lessons="lessons"
                  v-model="scheduleDay.end"
                />
              </td>
              <td>
                <DeliveryCheckbox v-model:checked="scheduleDay.canDeliverEarlier"/>
              </td>
              <td>
                <DeliveryCheckbox v-model:checked="scheduleDay.canPickUpLater"/>
              </td>
            </tr>
          </tbody>
        </table>
        <button class="btn btn-primary" @click="saveStandardScheduleTable">
          Zapisz
        </button>

        <h3 class="mt-5 mb-3">Niestandardowy plan lekcji</h3>
        <table class="table mb-3">
          <thead>
            <tr>
              <th scope="col">Data</th>
              <th scope="col">Dzień tygodnia</th>
              <th scope="col">Godzina rozpoczęcia</th>
              <th scope="col">Godzina zakończenia</th>
              <th scope="col">Można przywieźć wcześniej</th>
              <th scope="col">Można odebrać później</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="customDay in customSchedule" :key="customDay.id">
              <td>
                <input
                  :id="'customScheduleEntryDate-' + customDay.id"
                  class="form-control"
                  type="date"
                  :min="todayAsString()"
                  v-model="customDay.date"
                />
              </td>
              <td>{{ getDayOfWeekFromDate(customDay.date) }}</td>
              <td>
                <LessonPicker
                  type="START"
                  :lessons="lessons"
                  v-model="customDay.start"
                />
              </td>
              <td>
                <LessonPicker
                  type="END"
                  :lessons="lessons"
                  v-model="customDay.end"
                />
              </td>
              <td>
                <DeliveryCheckbox v-model:checked="customDay.canDeliverEarlier"/>
              </td>
              <td>
                <DeliveryCheckbox v-model:checked="customDay.canPickUpLater"/>
              </td>
              <td>
                <button
                  type="button"
                  class="btn-close"
                  aria-label="Usuń dzień z niestandardowego planu lekcji"
                  @click="removeCustomDay(customDay)"
                ></button>
              </td>
            </tr>
          </tbody>
        </table>
        <button class="btn btn-primary me-1" @click="saveCustomScheduleTable">
          Zapisz
        </button>
        <button class="btn btn-success" @click="addCustomScheduleEntry">
          Dodaj niestandardowy dzień
        </button>
      </div>
    </div>
  </PageContent>
</template>

<style scoped>
.cc-schedule-content {
  width: 80%;
}
</style>