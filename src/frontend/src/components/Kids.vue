<script>
import PageTitle from './page/PageTitle.vue';
import PageContent from './page/PageContent.vue';
export default {
    name: "Kids",
    components: { PageTitle, PageContent }
};
</script>

<template>
  <PageTitle title="Dzieci" />
  <PageContent>This is where you manage your Kids</PageContent>
</template>