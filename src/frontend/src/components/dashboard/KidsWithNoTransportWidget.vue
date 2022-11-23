<script>
import DashboardWidgetCard from "./DashboardWidgetCard.vue";
import { getNearestDays } from "../../utils.js";
import SingleDayMissingTransportInfo from "./SingleDayMissingTransportInfo.vue";

export default {
  components: { DashboardWidgetCard, SingleDayMissingTransportInfo },

  data() {
    return {
      days: this.calculateDays(),
      kidsWithNoTransports: {
        toSchool: [
          {
            dayId: 1,
            kids: ["Franek Koc"],
          },
          {
            dayId: 2,
            kids: ["Zuzia Winiarz", "Faustyna Koc", "Łucja Koc"],
          },
          {
            dayId: 4,
            kids: ["Marianna Książek"],
          },
        ],
        fromSchool: [
          {
            dayId: 3,
            kids: ["Zuzia Winiarz", "Faustyna Koc", "Łucja Koc"],
          },
          {
            dayId: 4,
            kids: ["Franek Koc", "Adaś Winiarz"],
          },
        ],
      },
    };
  },

  methods: {
    calculateDays() {
      return getNearestDays(5);
    },

    getMissingTransportToSchoolOnDay(day) {
        return this.kidsWithNoTransports.toSchool.find(element => element.dayId == day.id);
    },

    getMissingTransportFromSchoolOnDay(day) {
        return this.kidsWithNoTransports.fromSchool.find(element => element.dayId == day.id);
    }
  },
};
</script>

<template>
  <DashboardWidgetCard title="Dzieci bez transportu w najbliższych dniach">
    <table class="table mb-3">
      <thead>
        <tr>
          <th></th>
          <th v-for="day in days" :key="day.id" scope="col">
            {{ day.label }}
          </th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <th>Dowozy</th>
          <td v-for="day in days" :key="day.id">
            <SingleDayMissingTransportInfo :missingTransport="getMissingTransportToSchoolOnDay(day)"/>
          </td>
        </tr>
        <tr>
          <th>Odbiory</th>
          <td v-for="day in days" :key="day.id">
            <SingleDayMissingTransportInfo :missingTransport="getMissingTransportFromSchoolOnDay(day)"/>
          </td>
        </tr>
      </tbody>
    </table>
  </DashboardWidgetCard>
</template>