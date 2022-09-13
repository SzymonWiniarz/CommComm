<script>
import PageTitle from "../page/PageTitle.vue";
import PageContent from "../page/PageContent.vue";
import {useUserStore} from "../../stores/user_store";
import {mapState} from "pinia";

export default {
  name: "EditUser",
  components: {PageTitle, PageContent},

  props: ["id"],

  computed: {
    ...mapState(useUserStore, ["user"]),
  },

  methods: {
    updateUser() {
      alert("User updated!")
    }
  }

};
</script>

<template>
  <PageTitle title="Edytuj dane użytkownika"/>
  <PageContent>
    <div class="row justify-content-center">
      <form id="userForm" class="justify-content-center" @submit.prevent="updateUser">
        <div class="row mb-3">
          <div class="col">
            <label for="userFirstName" class="form-label">Imię</label>
            <input id="userFirstName" class="form-control" v-model="user.firstName" disabled>
          </div>
          <div class="col">
            <label for="userLastName" class="form-label">Nazwisko</label>
            <input id="userLastName" class="form-control" v-model="user.lastName" disabled>
          </div>
        </div>
        <div class="row mb-3">
          <div class="col">
            <label for="userEmail" class="form-label">Email</label>
            <input type="email" id="userEmail" class="form-control" v-model="user.email" required>
          </div>
          <div class="col">
            <label for="userPhone" class="form-label">Telefon</label>
            <input type="tel" id="userPhone" class="form-control" pattern="^\d{3}-\d{3}-\d{3}$" v-model="user.phone">
          </div>
        </div>
        <div class="row mb-3">
          <div class="form-check">
            <input class="form-check-input" type="checkbox" v-model="user.canNotifyByEmail" id="canNotifyUserByEmail">
            <label class="form-check-label" for="canNotifyUserByEmail">
              Powiadamiaj mnie emailowo
            </label>
          </div>
          <div class="form-check">
            <input class="form-check-input" type="checkbox" v-model="user.canNotifyBySms" id="canNotifyUserBySms">
            <label class="form-check-label" for="canNotifyUserBySms">
              Powiadamiaj mnie SMSowo
            </label>
          </div>
          <div class="form-check">
            <input class="form-check-input" type="checkbox" v-model="user.canNotifyByWhatsApp" id="canNotifyUserByWhatsApp">
            <label class="form-check-label" for="canNotifyUserByWhatsApp">
              Powiadamiaj mnie na WhatsApp
            </label>
          </div>
        </div>
        <button type="submit" class="btn btn-primary mb-3">Zapisz</button>
      </form>
    </div>
  </PageContent>
</template>

<style scoped>
#userForm {
  width: 80%;
}
</style>