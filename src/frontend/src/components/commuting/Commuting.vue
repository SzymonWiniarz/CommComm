<script>
import PageTitle from "../page/PageTitle.vue";
import PageContent from "../page/PageContent.vue";
export default {
  name: "Commuting",
  components: { PageTitle, PageContent },
};
</script>

<template>
  <PageTitle title="Przewozy" />
  <PageContent>
    <div class="row mb-5">
      <div class="col">
        <router-link to="/dowozy/dodaj" class="btn btn-success"
          >Zadeklaruj dowóz</router-link
        >
      </div>
    </div>
  </PageContent>
</template>