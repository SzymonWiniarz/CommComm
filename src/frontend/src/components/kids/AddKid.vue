<script>
import PageContent from "../page/PageContent.vue";
import PageTitle from "../page/PageTitle.vue";
import Form from "../Form.vue";
import KidForm from "./KidForm.vue";
import { mapActions, mapState } from "pinia";
import { useUserStore } from "../../stores/user_store";
import { useAlertsStore } from "../../stores/alerts_store";

export default {
  components: { PageContent, PageTitle, Form, KidForm },

  computed: {
    ...mapState(useUserStore, {
      user: "getUser",
    }),
  },

  methods: {
    ...mapActions(useAlertsStore, ["showAlert"]),

    kidCreated() {
      this.showAlert("Dodano nowe dziecko", "success");
      this.$router.push({ path: "/dzieci" });
    },

    testAlert() {
      this.showAlert("Test alert!", "danger");
    }
  },
};
</script>

<template>
  <PageTitle title="Dodaj dziecko" />
  <PageContent>
    <button @click="testAlert">Test alert</button>
    <KidForm
      :kidParam="{ lastName: user.lastName }"
      action="create"
      @submitted="kidCreated"
    />
  </PageContent>
</template>