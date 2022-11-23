<script>
import PageTitle from '../page/PageTitle.vue';
import PageContent from '../page/PageContent.vue';
import DashboardWidgetCard from './DashboardWidgetCard.vue';
import MyKidsCommutingWidget from './MyKidsCommutingWidget.vue';
import KidsWithNoTransportWidget from './KidsWithNoTransportWidget.vue';
export default {
    name: "Dashboard",
    components: { PageTitle, PageContent, DashboardWidgetCard, MyKidsCommutingWidget, KidsWithNoTransportWidget }
};
</script>

<template>
  <PageTitle title="Pulpit" />
  <PageContent>
    <MyKidsCommutingWidget/>
    <KidsWithNoTransportWidget/>
  </PageContent>
</template>