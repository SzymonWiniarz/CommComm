<script>
export default {
  props: {
    missingTransport: Object,
  },

  computed: {
    missingTransportInfo() {
      const kidsWithNoTransport = this.missingTransport
        ? this.missingTransport.kids
        : Array();

      if (kidsWithNoTransport && kidsWithNoTransport.length > 0) {
        return kidsWithNoTransport.join(", ");
      } else {
        return "-";
      }
    },
  },
};
</script>

<template>
    {{missingTransportInfo}}
</template>