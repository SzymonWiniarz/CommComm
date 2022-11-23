<script>
import DashboardWidgetCard from "./DashboardWidgetCard.vue";
import { getNearestDays } from "../../utils.js";
import SingleDayCommutingInfo from "./SingleDayCommutingInfo.vue";

export default {
  components: { DashboardWidgetCard, SingleDayCommutingInfo },

  data() {
    return {
      days: this.calculateDays(),
      kidsTransports: [
        {
          kid: {
            id: 1,
            name: "Zuzia",
          },
          transportsToSchool: [
            {
              dayId: 1,
              driver: "Andrzej Koc",
              time: "8:15",
            },
            {
              dayId: 2,
              driver: "Ja",
              time: "9:00",
            },
          ],
          transportsFromSchool: [
            {
              dayId: 1,
              driver: "Ja",
              time: "14:50",
            },
            {
              dayId: 2,
              driver: "Agnieszka Koc",
              time: "14:50",
            },
            {
              dayId: 3,
              driver: "Andrzej Koc",
              time: "16:00",
            }
          ],
        },
        {
          kid: {
            id: 2,
            name: "Adaś",
          },
        },
      ],
    };
  },

  methods: {
    calculateDays() {
      return getNearestDays(5);
    },

    getTransportToSchoolForDay(kidTransport, day) {
      const transportsToSchool = kidTransport.transportsToSchool ? kidTransport.transportsToSchool : Array();
      return transportsToSchool.find(transport => transport.dayId == day.id)
    },

    getTransportFromSchoolForDay(kidTransport, day) {
      const transportsFromSchool = kidTransport.transportsFromSchool ? kidTransport.transportsFromSchool : Array();
      return transportsFromSchool.find(transport => transport.dayId == day.id)
    }    
  },
};
</script>

<template>
  <DashboardWidgetCard title="Dowozy i odbiory moich dzieci na najbliższe dni">
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
        <tr v-for="kidTransport in kidsTransports" :key="kidTransport.kid.id">
          <th class="align-middle">{{ kidTransport.kid.name }}</th>
          <td v-for="day in days" :key="day.id">
            <SingleDayCommutingInfo :transportToSchool="getTransportToSchoolForDay(kidTransport, day)" :transportFromSchool="getTransportFromSchoolForDay(kidTransport, day)"/>
          </td>
        </tr>
      </tbody>
    </table>
  </DashboardWidgetCard>
</template>