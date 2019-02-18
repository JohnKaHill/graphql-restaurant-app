<template>
  <div class="wrapper">
    <div>Table #</div>
    <div>Status</div>
    <div>Drinks</div>
    <div>Meals</div>
    <div>Total</div>
    <div>More details</div>
  </div>
</template>

<script>
  export default {
    name: 'OrderHeader'

  }
</script>

<style scoped>
  .wrapper {
    display: grid;
    grid-template-columns: repeat(6, 1fr);
    grid-gap: 1em;
    background: #eee;
    padding: 1em;
    font-weight: bold;
    border-bottom: #000 1.5px solid
  }

</style>
