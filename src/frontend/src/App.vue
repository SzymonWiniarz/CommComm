<template>
  <p>
    <router-link to="/hello">Go to Hello</router-link>
    <router-link to="/bye">Go to Bye</router-link>
  </p>
  <router-view/>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>

</style>