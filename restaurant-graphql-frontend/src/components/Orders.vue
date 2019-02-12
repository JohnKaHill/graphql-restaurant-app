<template>
  <div class="orders">
    <ul>
      <li v-for="m in filterMessage" :key="m">{{ m }}</li>
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
  created () {
    this.$store.dispatch('findAllOrders')
  },
  computed: {
    ...mapState([
      'orders',
      'message'
    ]),
    filterMessage (filterOption) {
      if (filterOption === "Active") {
        return message.filter( item =>  
           item.includes('J')
        );
      } else if(filterOption === "Closed") {
        return message.filter( item =>  
           item.includes('J')
        );
      } else {
        return message
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