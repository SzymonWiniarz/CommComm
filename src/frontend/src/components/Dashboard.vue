<script>
import PageTitle from './page/PageTitle.vue';
import PageContent from './page/PageContent.vue';
export default {
    name: "Dashboard",
    components: { PageTitle, PageContent }
};
</script>

<template>
  <PageTitle title="Pulpit" />
  <PageContent>This is your Dashboard</PageContent>
</template>