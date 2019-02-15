<template>
  <div class="item-1" v-bind:class="{'is-open':orderItem.open}">
    <p>
      {{ orderItem.title }}
    </p>

  </div>
</template>

<script>
export default {
  name: "OrderItem",
  components: {

  }
}
</script>

<style scoped>
  #item-1 {
    padding: 20px;
  }

</style>
