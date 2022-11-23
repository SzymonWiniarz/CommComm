<script>
export default {
    props: {
        transportToSchool: Object,
        transportFromSchool: Object
    },

    computed: {
        deliversLabel() {
            return this.transportToSchool.driver == "Ja" ? "Zawożę " : "Zawozi ";
        },

        deliveryDriverLabel() {
            return this.transportToSchool.driver == "Ja" ? "Ja" : this.transportToSchool.driver
        },

        picksUpLabel() {
            return this.transportFromSchool.driver == "Ja" ? "Odbieram " : "Odbiera "
        },

        pickUpDriverLabel() {
            return this.transportFromSchool.driver == "Ja" ? "Ja" : this.transportFromSchool.driver
        },
    }
}
</script>

<template>
    <p v-if="transportToSchool">{{deliversLabel}} <b>{{deliveryDriverLabel}}</b> na godz. <b>{{transportToSchool.time}}</b></p>
    <p v-else class="cc-no-transport">Nikt jeszcze nie zadeklarował dowozu</p>

    <p v-if="transportFromSchool">{{picksUpLabel}} <b>{{pickUpDriverLabel}}</b> o godz. <b>{{transportFromSchool.time}}</b></p>
    <p v-else class="cc-no-transport">Nikt jeszcze nie zadeklarował odbioru</p>
</template>

<style lang="scss" scoped>
.cc-no-transport {
    color: $color-warning;
}
</style>