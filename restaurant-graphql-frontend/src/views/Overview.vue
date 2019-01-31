<template>
  <div class="overview">
    <div class="orderList">
      Show
      <select v-model="filterOption">
        <option value="Active">Active</option>
        <option value="Closed">Closed</option>
        <option value="All">All</option>
      </select>
      orders.
    </div>
    <br>
    <Orders v-bind:filterOption="filterOption"/>
  </div>
</template>

<script>
// @ is an alias to /src
import Orders from '@/components/Orders.vue'
import OrderHeader from '@/components/OrderHeader.vue'

export default {
  name: 'overview',
  data: () => ({
      filterOption: 'All'
  }),
  components: {
    Orders,
    OrderHeader
  }
  // beforeCreate() {
  //   this.$store.
    // this.$store.dispatch('fetchOrder', 001);
    // this.$store.dispatch('fetchOrders');
  // },
}
</script>

<style scoped>

.orderList {
  font-size: 14pt;
}

select {
  font-weight: bold;
  padding: 10px;
  margin-top: 10px;
  background-color: #fff;
  border: none;
  border-bottom: 2px solid #d04a02;
  font-size: 14pt;
}

select:focus{
  outline:0;
}

</style>
