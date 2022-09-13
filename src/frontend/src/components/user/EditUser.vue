<script>
import PageTitle from "../page/PageTitle.vue";
import PageContent from "../page/PageContent.vue";
import { useUserStore } from "../../stores/user_store";
import { mapActions } from "pinia";
import { mapState } from "pinia";

export default {
  name: "EditUser",
  components: { PageTitle, PageContent },

  props: ["id"],

  computed: {
    ...mapState(useUserStore, {
      localUser: "user",
    }),
  },

  methods: {
    ...mapActions(useUserStore, ["getById"]),

    getUserById(userId) {
      this.getById(userId)
    }
  },

  mounted() {
    this.getUserById(this.localUser.id);
  },
};
</script>

<template>
  <PageTitle title="Edytuj dane użytkownika" />
  <PageContent>
    <form id="userForm">
    <div class="row mb-3">
      <div class="col">
        <label for="userFirstName" class="form-label">Imię</label>
        <input id="userFirstName" class="form-control" :value="localUser.firstName" disabled>
      </div>
      <div class="col">
        <label for="userLastName" class="form-label">Imię</label>
        <input id="userLasttName" class="form-control" :value="localUser.lastName" disabled>
      </div>
    </div>
  </form>
  </PageContent>
</template>

<style scoped>
#userForm {
  width: 80%;
}
</style>