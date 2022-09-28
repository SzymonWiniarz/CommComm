<script>
import PageTitle from "../page/PageTitle.vue";
import PageContent from "../page/PageContent.vue";
import { mapActions } from "pinia";
import { useKidsStore } from "../../stores/kids_store";
import { useLessonsStore } from "../../stores/lessons_store";
import { useSchedulesStore } from "../../stores/schedules_store";
import { useAlertsStore } from "../../stores/alerts_store";

export default {
  components: { PageTitle, PageContent },

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

      schedule: [],
    };
  },

  methods: {
    ...mapActions(useKidsStore, ["getById"]),
    ...mapActions(useLessonsStore, ["getAllLessons"]),
    ...mapActions(useSchedulesStore, ["saveSchedule", "getSchedule"]),
    ...mapActions(useAlertsStore, ["showAlert"]),

    getKidName() {
      const kid = this.getById(this.id);
      return kid.firstName + " " + kid.lastName;
    },

    saveStandardSchedule() {
      this.saveSchedule(this.id, this.schedule);
      this.showAlert("Pomyślnie zaktualizowano standardowy plan lekcji", "success")
    },
  },

  created() {
    this.kidName = this.getKidName();
    this.lessons = this.getAllLessons();
    this.schedule = this.getSchedule(this.id);
  },
};
</script>

<template>
  <PageTitle title="Edytuj plan lekcji" />
  <PageContent>
    <h2 class="mb-5">Plan lekcji dla: {{ kidName }}</h2>
    <h3 class="mb-5">Standardowy plan lekcji</h3>
    <div class="row justify-content-center">
      <div class="cc-schedule-table">
        <table class="table">
          <thead>
            <tr>
              <th scope="col">Dzień tygodnia</th>
              <th scope="col">Godzina rozpoczęcia</th>
              <th scope="col">Godzina zakończenia</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="day in daysOfWeek" :key="day.id">
              <td>{{ day.name }}</td>
              <td>
                <select
                  class="form-select"
                  aria-label="Wybierz godzinę rozpoczęcia lekcji"
                  v-model="
                    schedule.find((scheduleDay) => scheduleDay.dayId == day.id)
                      .start
                  "
                >
                  <option disabled selected value="">
                    Wybierz godzinę rozpoczęcia lekcji
                  </option>
                  <option
                    v-for="lesson in lessons"
                    :value="lesson.id"
                    :key="lesson.id"
                  >
                    {{ lesson.startsAt }}
                  </option>
                </select>
              </td>
              <td>
                <select
                  class="form-select"
                  aria-label="Wybierz godzinę zakończenia lekcji"
                  v-model="
                    schedule.find((scheduleDay) => scheduleDay.dayId == day.id)
                      .end
                  "
                >
                  <option disabled selected value="">
                    Wybierz godzinę zakończenia lekcji
                  </option>
                  <option
                    v-for="lesson in lessons"
                    :value="lesson.id"
                    :key="lesson.id"
                  >
                    {{ lesson.endsAt }}
                  </option>
                </select>
              </td>
            </tr>
          </tbody>
        </table>
        <button class="btn btn-primary" @click="saveStandardSchedule">
          Zapisz
        </button>
      </div>
    </div>
  </PageContent>
</template>

<style scoped>
.cc-schedule-table {
  width: 80%;
}
</style>