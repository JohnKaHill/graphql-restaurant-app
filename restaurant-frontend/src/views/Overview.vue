<template>
  <div class="overview">
    <div class="orderList">
      Show
      <v-select
        @input="setFilterOption"
        :options="this.$store.state.filterOptions"
        :value="this.$store.state.selectedFilterOption"
      ></v-select>
      orders.
    </div>
    <br>
    <Orders/>
  </div>
</template>

<script>
// @ is an alias to /src
import Orders from '@/components/Orders.vue'

export default {
  name: 'overview',
  methods: {
    setFilterOption(val) {
      this.$store.dispatch('setFilterOption', val)
    }
  },
  components: {
    Orders
  }
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
