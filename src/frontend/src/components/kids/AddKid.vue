<script>
import PageContent from "../page/PageContent.vue";
import PageTitle from "../page/PageTitle.vue";
import Form from "../Form.vue";
import KidForm from "./KidForm.vue";
import { mapState } from "pinia";
import { useUserStore } from "../../stores/user_store";

export default {
  components: { PageContent, PageTitle, Form, KidForm },

  computed: {
    ...mapState(useUserStore, {
      user: "getUser",
    }),
  },
};
</script>

<template>
  <PageTitle title="Dodaj dziecko" />
  <PageContent>
    <KidForm :kidParam="{ lastName: user.lastName }" />
  </PageContent>
</template>