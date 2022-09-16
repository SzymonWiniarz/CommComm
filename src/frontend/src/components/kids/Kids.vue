<script>
import PageTitle from "../page/PageTitle.vue";
import PageContent from "../page/PageContent.vue";
export default {
  name: "Kids",
  components: { PageTitle, PageContent },
};
</script>

<template>
  <PageTitle title="Dzieci" />
  <PageContent>
    <div class="row mb-3">
      <div class="col">
        <router-link to="/dzieci/dodaj" class="btn btn-success">Dodaj</router-link>
      </div>
    </div>
  </PageContent>
</template>