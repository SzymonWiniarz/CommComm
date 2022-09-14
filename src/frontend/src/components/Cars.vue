<script>
import PageTitle from "./page/PageTitle.vue";
import PageContent from "./page/PageContent.vue";

export default {
  name: "Cars",
  components: { PageTitle, PageContent },
};
</script>

<template>
  <PageTitle title="Samochody" />
  <PageContent>This is where you manage your Cars</PageContent>
</template>