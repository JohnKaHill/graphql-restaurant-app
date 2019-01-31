<template>
  <div class="orders">
    <ul>
      <li>
        <div class="wrapper-header">
           <div>Table #</div>
           <div>Status</div>
           <div>Drinks</div>
           <div>Meals</div>
           <div>Total</div>
           <div>More details</div>
        </div>
      </li>
      <li class="wrapper" v-bind:key="order.orderId"
           v-for="order in filteredOrders">
        <div> {{ order.tableNumber }} </div>
        <div> {{ order.isOpen }} </div>
        <div>
          <ul>
            <li v-for="beverage in order.drinks" v-bind:key="beverage.name">
              {{ beverage.name }} - {{ beverage.price }} €</li>
          </ul>
        </div>
        <div>
          <ul>
            <li v-for="food in order.meals" v-bind:key="food.name">
              {{ food.name }} - {{ food.price }} €</li>
          </ul>
        </div>
        <div>

        </div>
        <div> Details </div>

      </li>
    </ul>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: "Orders",
  props: {
    filterOption: {
      type: String,
      default: "All"
    }
  },
  computed: {
    ...mapState(['order', 'orders']),
    filteredOrders: function (filterOption) {
      if (filterOption === "Active") {
        return this.orders.filter(function(item) {
          return item.isOpen
        });
      } else if(filterOption === "Closed") {
        return this.orders.filter(function(item) {
          return !item.isOpen
        });
      } else {
        return this.orders
      }
    }
  }
}
</script>

<style scoped>
  #header {
    padding: 20px;
  }

  ul {
    list-style: none;
    padding-left: 0;
  }

  .wrapper, .wrapper-header {
    display: grid;
    grid-template-columns: repeat(6, 1fr);
    border-bottom: #ddd 0.5px solid
  }

  .wrapper-header {
    grid-gap: 1em;
    background: #eee;
    padding: 1em;
    font-weight: bold;
    border-bottom: #000 1.5px solid
  }

  .wrapper > div {
    background: #eee;
    padding: 1em;
  }

  .wrapper > div:nth-child(odd) {
    background: #ddd;
  }

</style>
